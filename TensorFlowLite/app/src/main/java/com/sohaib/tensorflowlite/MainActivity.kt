package com.sohaib.tensorflowlite

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sohaib.tensorflowlite.databinding.ActivityMainBinding
import com.sohaib.tensorflowlite.manager.TFLiteManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val tfLiteManager by lazy { TFLiteManager(this) }
    private val imageRes by lazy { R.drawable.img_detector_fruits }
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreen()
        updateUI()

        binding.mbResize.setOnClickListener { objectDetection() }
    }

    private fun updateUI() {
        binding.imageView.setImageResource(imageRes)
    }

    private fun fullScreen() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun objectDetection() {
        val list = listOf(
            R.drawable.img_detector_fruits,
            R.drawable.img_detector_girl,
            R.drawable.img_detector_people,
        )

        if (counter >= list.size) { counter = 0 }

        val bitmap = BitmapFactory.decodeResource(resources, list[counter])
        CoroutineScope(Dispatchers.Default).launch {
            val newBitmap = tfLiteManager.detectObjects(bitmap)
            withContext(Dispatchers.Main) {
                binding.imageViewResized.setImageBitmap(newBitmap)
                counter++
            }
        }
    }
}