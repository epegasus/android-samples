package com.sohaib.imageclassification

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.sohaib.imageclassification.ml.ImageClassifier
import com.sohaib.imageclassification.repository.ClassifierRepository
import com.sohaib.imageclassification.ui.MainScreen
import com.sohaib.imageclassification.ui.theme.ImageClassificationTensorFlowLiteTheme
import com.sohaib.imageclassification.viewmodel.MainViewModel

/**
 * Main Activity for the Image Classification app
 * Sets up the Compose UI and dependency injection
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.i("ImageClassifierApp", "[App] MainActivity onCreate")
        // Initialize dependencies (simple dependency injection)
        val imageClassifier = ImageClassifier(this)
        val repository = ClassifierRepository(imageClassifier)
        val viewModel = MainViewModel(repository)

        setContent {
            ImageClassificationTensorFlowLiteTheme {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}