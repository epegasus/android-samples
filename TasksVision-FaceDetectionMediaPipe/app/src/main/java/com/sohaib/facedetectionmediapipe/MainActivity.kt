package com.sohaib.facedetectionmediapipe

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sohaib.facedetectionmediapipe.databinding.ActivityMainBinding
import com.sohaib.facedetectionmediapipe.helper.FaceDetectorHelper
import com.sohaib.facedetectionmediapipe.helper.FaceDetectorHelper.Companion.TAG

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val faceDetectorHelper by lazy { FaceDetectorHelper(context = this) }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        binding.sivImage.setImageURI(uri)
        validateUri(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreen()

        binding.mbChooseImage.setOnClickListener { getContent.launch("image/*") }
    }

    private fun fullScreen() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun validateUri(uri: Uri?) {
        when (uri == null) {
            true -> Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show()
            false -> detectFace(uri)
        }
    }

    @Suppress("DEPRECATION")
    private fun detectFace(uri: Uri) {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
        else MediaStore.Images.Media.getBitmap(contentResolver, uri)

        val result = faceDetectorHelper.detectImage(bitmap)

        Log.d(TAG, "detectFace: Image Width: ${result?.inputImageWidth}")
        Log.d(TAG, "detectFace: Image Height: ${result?.inputImageHeight}")
        Log.d(TAG, "detectFace: Result Size: ${result?.results?.size}")

        binding.sivOutputImage.setImageBitmap(result?.resultBitmap)
        result?.results?.forEach {
            Log.d(TAG, "detectFace: TimeStampMillis: ${it.timestampMs()}")
            Log.d(TAG, "detectFace: Detections: ${it.detections()}")
        }
    }

    override fun onDestroy() {
        faceDetectorHelper.clearFaceDetector()
        super.onDestroy()
    }
}