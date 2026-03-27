package com.sohaib.opencv.manager

import android.graphics.Bitmap
import android.util.Log
import com.sohaib.opencv.utils.TAG
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import java.io.ByteArrayOutputStream
import androidx.core.graphics.createBitmap

class OpenCvManager {

    fun initOpenCv(callback: () -> Unit) {
        if (OpenCVLoader.initLocal()) {
            callback.invoke()
            Log.i(TAG, "OpenCV successfully loaded.");
        }
    }

    fun getResizedBitmap(imageBitmap: Bitmap?, newWidth: Int, newHeight: Int): Bitmap? {
        if (imageBitmap == null) {
            Log.e(TAG, "getResizedBitmap: Source bitmap is null")
            return null
        }

        getBitmapSizeInKB(imageBitmap, "Original")

        val resizedBitmap = resizedBitmap(imageBitmap, newWidth, newHeight)

        getBitmapSizeInKB(resizedBitmap, "Resized")

        return resizedBitmap
    }

    private fun resizedBitmap(imageBitmap: Bitmap?, newWidth: Int, newHeight: Int): Bitmap {
        // Convert the input Bitmap to a Mat
        val inputMat = Mat()
        Utils.bitmapToMat(imageBitmap, inputMat)

        // Create a new Mat for the resized image
        val resizedMat = Mat()
        Imgproc.resize(inputMat, resizedMat, Size(newWidth.toDouble(), newHeight.toDouble()))

        // Convert the resized Mat back to a Bitmap
        val resizedBitmap = createBitmap(newWidth, newHeight)
        Utils.matToBitmap(resizedMat, resizedBitmap)

        // Release Mat resources
        inputMat.release()
        resizedMat.release()
        return resizedBitmap
    }

    private fun getBitmapSizeInKB(bitmap: Bitmap, text: String) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) // Compress to JPEG with 100% quality
        val byteArray = stream.toByteArray()
        val size = byteArray.size / 1024 // Convert bytes to KB
        Log.d(TAG, "getResizedBitmap: $text image size: ${bitmap.width}x${bitmap.height}, Memory: $size KB")
    }
}