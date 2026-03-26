package com.sohaib.imageclassification.viewmodel

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohaib.imageclassification.data.UiState
import com.sohaib.imageclassification.repository.ClassifierRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the main screen
 * Manages UI state and handles business logic for image classification
 * Follows MVVM architecture pattern
 */
class MainViewModel(private val repository: ClassifierRepository) : ViewModel() {
    companion object {
        private const val LOG_TAG: String = "ImageClassifierApp"
    }

    // Private mutable state flow for internal state management
    private val _uiState = MutableStateFlow(UiState())

    // Public read-only state flow for UI observation
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    /**
     * Set the selected image URI and trigger classification
     * @param uri The URI of the selected image
     */
    fun setImageUri(uri: Uri) {
        Log.i(LOG_TAG, "[VM] Image selected: $uri")
        _uiState.value = _uiState.value.copy(
            selectedImageUri = uri.toString(),
            errorMessage = null
        )

        // Start classification process
        classifyImage(uri)
    }

    /**
     * Classify the selected image
     * Handles the complete flow from URI to classification results
     * @param uri The URI of the image to classify
     */
    private fun classifyImage(uri: Uri) = viewModelScope.launch {
        try {
            // Set loading state
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            Log.d(LOG_TAG, "[VM] Starting classification for uri=$uri")

            // Get correctly oriented bitmap
            val bitmap = repository.getCorrectlyOrientedBitmap(uri)

            if (bitmap != null) {
                // Check if classifier is ready
                if (!repository.isReady()) {
                    Log.e(LOG_TAG, "[VM] Classifier not ready (model failed to load)")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Model not loaded. Replace placeholder TFLite with a real model."
                    )
                    return@launch
                }
                // Classify the image
                val results = repository.classifyImage(bitmap)
                Log.i(LOG_TAG, "[VM] Classification results count=${results.size}")

                // Update state with results
                _uiState.value = _uiState.value.copy(classificationResults = results, isLoading = false)
                if (results.isEmpty()) {
                    Log.w(LOG_TAG, "[VM] No predictions returned by classifier")
                    _uiState.value = _uiState.value.copy(errorMessage = "No predictions. Check model file and image.")
                }
            } else {
                // Handle bitmap loading failure
                Log.e(LOG_TAG, "[VM] Failed to load bitmap from uri=$uri")
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Failed to load image")
            }

        } catch (e: Exception) {
            // Handle classification errors
            Log.e(LOG_TAG, "[VM] Classification failed: ${e.message}", e)
            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Classification failed: ${e.message}")
        }
    }

    /**
     * Clear the current results and reset to initial state
     */
    fun clearResults() {
        _uiState.value = UiState()
    }

    /**
     * Retry classification with the current image
     * Useful when classification fails
     */
    fun retryClassification() {
        val currentUri = _uiState.value.selectedImageUri
        if (currentUri != null) {
            classifyImage(currentUri.toUri())
        }
    }
}
