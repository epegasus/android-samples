package com.sohaib.imageclassification.repository

import android.graphics.Bitmap
import android.util.Log
import com.sohaib.imageclassification.data.ClassificationResult
import com.sohaib.imageclassification.ml.ImageClassifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class that handles image classification operations
 * Acts as a data layer between the ViewModel and the ML model
 * Handles background processing for image classification
 */
class ClassifierRepository(private val imageClassifier: ImageClassifier) {
    companion object {
        private const val LOG_TAG: String = "ImageClassifierApp"
    }
    
    /**
     * Classify an image using the TensorFlow Lite model
     * This function runs on a background thread to avoid blocking the UI
     * @param bitmap The image bitmap to classify
     * @return List of classification results with labels and confidence scores
     */
    suspend fun classifyImage(bitmap: Bitmap): List<ClassificationResult> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(LOG_TAG, "[Repo] classifyImage on background thread")
                val results = imageClassifier.classifyImage(bitmap)
                Log.d(LOG_TAG, "[Repo] classifyImage results size=${results.size}")
                results
            } catch (e: Exception) {
                Log.e(LOG_TAG, "[Repo] classifyImage error: ${e.message}", e)
                emptyList()
            }
        }
    }
    
    /**
     * Get a correctly oriented bitmap from URI
     * Handles EXIF rotation data to ensure proper image orientation
     * @param uri The image URI
     * @return Correctly oriented bitmap or null if failed
     */
    suspend fun getCorrectlyOrientedBitmap(uri: android.net.Uri): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(LOG_TAG, "[Repo] getCorrectlyOrientedBitmap: $uri")
                val bmp = imageClassifier.getCorrectlyOrientedBitmap(uri)
                if (bmp == null) Log.e(LOG_TAG, "[Repo] Failed to decode/rotate bitmap for uri=$uri")
                bmp
            } catch (e: Exception) {
                Log.e(LOG_TAG, "[Repo] getCorrectlyOrientedBitmap error: ${e.message}", e)
                null
            }
        }
    }

    /** Returns true if the underlying ML classifier is ready. */
    fun isReady(): Boolean = imageClassifier.isReady()
}
