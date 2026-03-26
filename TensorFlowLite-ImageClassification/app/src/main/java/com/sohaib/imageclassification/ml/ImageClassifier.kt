package com.sohaib.imageclassification.ml

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import com.sohaib.imageclassification.data.ClassificationResult
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.IOException
import java.io.InputStream
import androidx.core.graphics.scale

/**
 * ImageClassifier class that handles TensorFlow Lite model loading and inference
 * Uses MobileNet v1 model for image classification
 */
class ImageClassifier(private val context: Context) {

    companion object {
        private const val LOG_TAG: String = "ImageClassifierApp"
    }
    
    private var classifier: ImageClassifier? = null
    
    init {
        initializeClassifier()
    }
    
    /**
     * Initialize the TensorFlow Lite classifier with the MobileNet model
     */
    private fun initializeClassifier() {
        try {
            val options = ImageClassifier.ImageClassifierOptions.builder()
                .setMaxResults(10)
                .build()
                
            classifier = ImageClassifier.createFromFileAndOptions(context, "models/mobilenet_v3.tflite", options)
            Log.i(LOG_TAG, "[ML] TFLite classifier initialized successfully")
        } catch (e: Exception) {
            Log.e(LOG_TAG, "[ML] Failed to initialize TFLite classifier: ${e.message}", e)
        }
    }
    
    /**
     * Classify an image and return the top predictions
     * @param bitmap The input image bitmap
     * @return List of ClassificationResult with label and confidence
     */
    fun classifyImage(bitmap: Bitmap): List<ClassificationResult> {
        return try {
            if (classifier == null) {
                Log.e(LOG_TAG, "[ML] Classifier is null. Did the model fail to load?")
                return emptyList()
            }
            Log.d(LOG_TAG, "[ML] classifyImage: input bitmap ${bitmap.width}x${bitmap.height}")
            // Preprocess the image to the required size (224x224)
            val processedBitmap = preprocessImage(bitmap)
            Log.d(LOG_TAG, "[ML] Preprocessed bitmap: ${processedBitmap.width}x${processedBitmap.height}")
            // Convert bitmap to TensorImage
            val tensorImage = TensorImage.fromBitmap(processedBitmap)
            
            // Run inference
            val classifications = classifier?.classify(tensorImage)
            Log.d(LOG_TAG, "[ML] Raw classifications size: ${classifications?.size ?: 0}")

            // Each classification contains a list of categories; flatten and take top N by score
            val top = classifications
                ?.flatMap { it.categories }
                ?.sortedByDescending { it.score }
                ?.take(10)
                 ?.map { cat ->
                    // Some models may return scores outside [0,1] (e.g., 0..100). Normalize here.
                    val normalized = if (cat.score > 1f) cat.score / 100f else cat.score
                    Log.d(LOG_TAG, "classifyImage: ${cat.label} raw=${String.format("%.4f", cat.score)} norm=${String.format("%.4f", normalized)}")
                     ClassificationResult(label = cat.label, confidence = normalized, rawScore = cat.score)
                }
                .orEmpty()

            Log.i(LOG_TAG, "[ML] Top results: ${top.joinToString { it.label + "=" + String.format("%.1f%%", it.confidence.coerceIn(0f,1f)*100f) }}")
            top
            
        } catch (e: Exception) {
            Log.e(LOG_TAG, "[ML] classifyImage error: ${e.message}", e)
            emptyList()
        }
    }
    
    /**
     * Preprocess the image to the required input size (224x224)
     * and normalize pixel values
     */
    private fun preprocessImage(bitmap: Bitmap): Bitmap {
        // Resize to 224x224 (MobileNet input size)
        val resizedBitmap = bitmap.scale(224, 224)
        
        // Create image processor for normalization
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .build()
            
        // Convert to TensorImage and process
        val tensorImage = TensorImage.fromBitmap(resizedBitmap)
        val processedTensorImage = imageProcessor.process(tensorImage)
        
        return processedTensorImage.bitmap
    }
    
    /**
     * Handle image rotation based on EXIF data
     * This ensures images are displayed and processed in the correct orientation
     */
    fun getCorrectlyOrientedBitmap(uri: android.net.Uri): Bitmap? {
        return try {
            Log.d(LOG_TAG, "[ML] Loading bitmap from uri=$uri")
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                Log.e(LOG_TAG, "[ML] Failed to open InputStream for uri=$uri")
                return null
            }
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            if (bitmap == null) {
                Log.e(LOG_TAG, "[ML] BitmapFactory.decodeStream returned null for uri=$uri")
                return null
            }
            
            // Read EXIF data to determine orientation
            val exifInputStream = context.contentResolver.openInputStream(uri)
            if (exifInputStream == null) {
                Log.w(LOG_TAG, "[ML] EXIF input stream is null, returning original bitmap")
                return bitmap
            }
            val exif = ExifInterface(exifInputStream)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            exifInputStream.close()
            
            // Apply rotation if needed
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.postScale(-1f, 1f)
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1f, -1f)
                else -> {
                    Log.d(LOG_TAG, "[ML] No EXIF rotation needed (orientation=$orientation)")
                    return bitmap
                }
            }
            
            val rotated = Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
            )
            Log.d(LOG_TAG, "[ML] Rotated bitmap to ${rotated.width}x${rotated.height}")
            rotated
        } catch (e: IOException) {
            Log.e(LOG_TAG, "[ML] getCorrectlyOrientedBitmap IO error: ${e.message}", e)
            null
        }
    }
    
    /**
     * Clean up resources
     */
    fun cleanup() {
        classifier?.close()
    }

    /**
     * Returns true if the underlying TFLite classifier is initialized.
     */
    fun isReady(): Boolean {
        val ready = classifier != null
        if (!ready) Log.e(LOG_TAG, "[ML] isReady=false (classifier is null)")
        return ready
    }
}
