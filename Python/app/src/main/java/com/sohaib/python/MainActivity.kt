package com.sohaib.python

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.sohaib.python.databinding.ActivityMainBinding

private const val TAG = "MyTag"

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // "context" must be an Activity, Service or Application object from your app.
        // Starting Python
        if (!Python.isStarted()) {
            try {
                Python.start(AndroidPlatform(this))
            } catch (e: IllegalStateException) {
                // If failed to start
                Log.d(TAG, "onCreate: IllegalStateException: ${e.message}")
            } catch (e: Exception) {
                // if error found during starting
                Log.d(TAG, "onCreate: Exception: ${e.message}")
            }
        }

        try {
            createInstance()
        } catch (e: RuntimeException) {
            Log.d(TAG, "onCreate: RuntimeException: ${e.message}")
        }
    }

    @Throws(RuntimeException::class)
    private fun createInstance() {
        val py = Python.getInstance()

        // Pick python script by giving name
        val pyObject = py.getModule("myFirstScript")

        // Call function Name
        binding.tvText.text = pyObject.callAttr("main", "Yahoo!").toString()
    }
}