package com.sohaib.collectionwidget

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sohaib.collectionwidget.widget.EXTRA_ITEM
import com.sohaib.collectionwidget.widget.TAG

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreen()
        checkIntent()
    }

    private fun fullScreen() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkIntent() {
        val value = intent.getStringExtra(EXTRA_ITEM)
        Log.d(TAG, "checkIntent: $value")

        if (value == null) return
        val browserIntent = Intent(Intent.ACTION_VIEW, value.toUri())
        startActivity(browserIntent)
    }
}