package com.sohaib.facedetectionmediapipe.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.SystemClock
import android.util.Log
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.components.containers.Detection
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult

class FaceDetectorHelper(val context: Context) {

    private var threshold = THRESHOLD_DEFAULT
    private var runningMode = RunningMode.IMAGE
    private var currentDelegate = DELEGATE_CPU

    private var faceDetector: FaceDetector? = null

    init {
        setupFaceDetector()
    }

    private fun setupFaceDetector() {
        val baseOptionsBuilder = BaseOptions.builder()

        when (currentDelegate) {
            DELEGATE_CPU -> baseOptionsBuilder.setDelegate(Delegate.CPU)
            DELEGATE_GPU -> baseOptionsBuilder.setDelegate(Delegate.GPU)
        }

        //val modelName = "face_detection_short_range.tflite"
        val modelName = "blaze_face_short_range.tflite"
        baseOptionsBuilder.setModelAssetPath(modelName)

        try {
            val optionsBuilder = FaceDetector.FaceDetectorOptions.builder()
                .setBaseOptions(baseOptionsBuilder.build())
                .setMinDetectionConfidence(threshold)
                .setRunningMode(runningMode)

            when (runningMode) {
                RunningMode.IMAGE,
                RunningMode.VIDEO -> optionsBuilder.setRunningMode(runningMode)

                RunningMode.LIVE_STREAM -> {}
            }

            val options = optionsBuilder.build()
            faceDetector = FaceDetector.createFromOptions(context, options)

        } catch (e: IllegalStateException) {
            Log.e(TAG, "TFLite failed to load model with error: " + e.message)
        } catch (e: RuntimeException) {
            Log.e(TAG, "Face detector failed to load model with error: " + e.message)
        }
    }

    fun detectImage(image: Bitmap): ResultBundle? {
        if (runningMode != RunningMode.IMAGE) {
            throw IllegalArgumentException("Attempting to call detectImage while not using RunningMode.IMAGE")
        }

        if (faceDetector == null) {
            Log.d(TAG, "detectImage: FaceDetector is null")
            return null
        }

        val convertedImage = if (image.config != Bitmap.Config.ARGB_8888) {
            image.copy(Bitmap.Config.ARGB_8888, true)
        } else {
            image
        }

        val startTime = SystemClock.uptimeMillis()
        val mpImage = BitmapImageBuilder(convertedImage).build()
        faceDetector?.detect(mpImage)?.also { detectionResult ->
            val inferenceTimeMs = SystemClock.uptimeMillis() - startTime
            val resultBitmap = drawBoundingBoxesOnFaces(image, detectionResult.detections())
            return ResultBundle(listOf(detectionResult), inferenceTimeMs, image.height, image.width, resultBitmap)
        }
        return null
    }

    private fun drawBoundingBoxesOnFaces(bitmap: Bitmap, results: List<Detection>): Bitmap {
        // Create a mutable copy of the original image to draw on it
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paddingPx = dpToPx(200f, context)

        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        for (result in results) {
            val boundingBox = result.boundingBox()

            // Expand the bounding box by adding padding to each side
            val expandedBoundingBox = RectF(
                boundingBox.left - paddingPx,
                boundingBox.top - paddingPx,
                boundingBox.right + paddingPx,
                boundingBox.bottom + paddingPx
            )

            // Ensure the expanded box doesn't go out of image boundaries
            expandedBoundingBox.intersect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat())

            canvas.drawRect(expandedBoundingBox, paint)
        }
        return mutableBitmap
    }

    fun dpToPx(dp: Float, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }

    fun isClosed(): Boolean {
        return faceDetector == null
    }

    fun clearFaceDetector() {
        faceDetector?.close()
        faceDetector = null
    }

    data class ResultBundle(
        val results: List<FaceDetectorResult>,
        val inferenceTime: Long,
        val inputImageHeight: Int,
        val inputImageWidth: Int,
        val resultBitmap: Bitmap? = null
    )

    companion object {
        const val TAG = "FaceDetectorHelper"
        const val THRESHOLD_DEFAULT = 0.5F
        const val DELEGATE_CPU = 0
        const val DELEGATE_GPU = 1
    }
}