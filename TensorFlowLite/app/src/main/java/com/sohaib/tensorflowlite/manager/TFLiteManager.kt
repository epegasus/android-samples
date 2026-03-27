package com.sohaib.tensorflowlite.manager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import com.sohaib.tensorflowlite.dataClasses.DetectionResult
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector

const val TAG = "MyTag"

class TFLiteManager(private val context: Context) {

    fun detectObjects(bitmap: Bitmap?): Bitmap? {
        if (bitmap == null) {
            Log.e(TAG, "detectObjects: Source Bitmap is null")
            return null
        }

        val image = TensorImage.fromBitmap(bitmap)

        // Step 2: Initialize the detector object
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(50)
            .setScoreThreshold(0.1f)
            .build()

        val detector = ObjectDetector.createFromFileAndOptions(context, "model.tflite", options)

        // Step 3: Feed given image to the detector
        val results = detector.detect(image)
        return detectResults(bitmap, results)
    }

    private fun detectResults(bitmap: Bitmap, results: List<Detection>): Bitmap {
        debugPrint(results)

        val resultToDisplay = results.map {
            // Get the top-1 category and craft the display text
            val category = it.categories.first()
            val text = "${category.label}, ${category.score.times(100).toInt()}%"

            // Create a data object to display the detection result
            DetectionResult(it.boundingBox, text)
        }
        // Draw the detection result on the bitmap and show it.
        return drawDetectionResult(bitmap, resultToDisplay)
    }

    private fun drawDetectionResult(bitmap: Bitmap, resultToDisplay: List<DetectionResult>): Bitmap {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 64f
            typeface = Typeface.DEFAULT_BOLD
        }

        for (result in resultToDisplay) {
            // Draw the bounding box
            canvas.drawRect(result.boundingBox, paint)

            // Draw the label text
            val textX = result.boundingBox.left
            val textY = result.boundingBox.top - 10f // Offset the text above the box
            canvas.drawText(result.text, textX, textY, textPaint)
        }
        return mutableBitmap
    }

    private fun debugPrint(results: List<Detection>) {
        for ((i, obj) in results.withIndex()) {
            val box = obj.boundingBox

            Log.d(TAG, "Detected object: $i ")
            Log.d(TAG, "  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})")

            for ((j, category) in obj.categories.withIndex()) {
                Log.d(TAG, "    Label $j: ${category.label}")
                val confidence: Int = category.score.times(100).toInt()
                Log.d(TAG, "    Confidence: ${confidence}%")
            }
        }
    }
}