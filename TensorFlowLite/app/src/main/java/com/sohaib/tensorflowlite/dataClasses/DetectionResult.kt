package com.sohaib.tensorflowlite.dataClasses

import android.graphics.RectF

/**
 *   Developer: Sohaib Ahmed
 *   Date: 12/3/2024
 *   Profile:
 *     -> github.com/epegasus
 *     -> linkedin.com/in/epegasus
 */

data class DetectionResult(
    val boundingBox: RectF,
    val text: String
)
