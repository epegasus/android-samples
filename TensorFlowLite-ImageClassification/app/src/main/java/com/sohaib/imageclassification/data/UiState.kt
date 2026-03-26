package com.sohaib.imageclassification.data

/**
 * Represents the UI state for the main screen
 * Contains all the data needed to render the UI
 */
data class UiState(
    val selectedImageUri: String? = null,
    val classificationResults: List<ClassificationResult> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
