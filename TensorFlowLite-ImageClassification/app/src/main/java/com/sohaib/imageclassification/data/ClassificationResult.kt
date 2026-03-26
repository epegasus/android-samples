package com.sohaib.imageclassification.data

import java.util.Locale

/**
 * Data class representing a single classification result
 * Contains the predicted label and confidence score
 */
data class ClassificationResult(val label: String, val confidence: Float, val rawScore: Float? = null) {
    /**
     * Returns the confidence as a percentage string
     */
    fun getConfidencePercentage(): String {
        val clamped = confidence.coerceIn(0f, 1f)
        val pct = clamped * 100f
        val displayed = if (clamped < 1f) kotlin.math.floor(pct * 10f) / 10f else 100f
        return String.format(Locale.ENGLISH, "%.1f%%", displayed)
    }

    fun getRawScoreString(): String {
        val v = rawScore ?: confidence
        return String.format(Locale.ENGLISH, "%.4f", v)
    }
}
