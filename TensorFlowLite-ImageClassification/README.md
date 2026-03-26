# Image Classification TensorFlow Lite App

A simple Android app that uses TensorFlow Lite to classify images using the MobileNet v1 model. Built with Kotlin, MVVM architecture, Jetpack Compose, and Material 3.

## Features

- **Image Selection**: Choose images from the device gallery
- **Real-time Classification**: Uses TensorFlow Lite MobileNet v1 model for image classification
- **Modern UI**: Built with Jetpack Compose and Material 3 design system
- **MVVM Architecture**: Clean separation of concerns with ViewModel and Repository pattern
- **EXIF Support**: Handles image rotation correctly
- **Error Handling**: Comprehensive error handling and retry functionality

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 23 (Android 6.0) or higher
- Kotlin 1.8+

### Setup Instructions

1. **Download the Model**: 
   - The current model file is a placeholder. To use the app, you need to download the actual MobileNet v1 model:
   - Visit [TensorFlow Hub](https://tfhub.dev/google/lite-model/imagenet/mobilenet_v1_100_224/classification/1/default/1)
   - Download the `.tflite` file
   - Replace the placeholder file at `app/src/main/assets/models/mobilenet_v1_1.0_224.tflite`

2. **Build and Run**:
   ```bash
   ./gradlew assembleDebug
   ```

3. **Install on Device**:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## Project Structure

```
app/src/main/java/com/example/image/classification/tensorflowlite/
├── data/
│   ├── ClassificationResult.kt      # Data class for classification results
│   └── UiState.kt                   # UI state management
├── ml/
│   └── ImageClassifier.kt          # TensorFlow Lite model wrapper
├── repository/
│   └── ClassifierRepository.kt     # Data layer for image classification
├── ui/
│   └── MainScreen.kt               # Jetpack Compose UI
├── viewmodel/
│   └── MainViewModel.kt            # ViewModel for UI state management
└── MainActivity.kt                 # Main activity with Compose setup
```

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern:

- **Model**: TensorFlow Lite model and data classes
- **View**: Jetpack Compose UI components
- **ViewModel**: Manages UI state and business logic
- **Repository**: Handles data operations and ML model interactions

## Key Components

### ImageClassifier
- Loads the TensorFlow Lite model from assets
- Handles image preprocessing (resize to 224x224, normalization)
- Manages EXIF rotation data
- Performs inference and returns classification results

### MainViewModel
- Manages UI state using StateFlow
- Handles image selection and classification workflow
- Provides error handling and retry functionality

### MainScreen (Compose UI)
- Material 3 design system
- Image picker integration
- Real-time classification results display
- Loading states and error handling

## How to Replace the Model

To use a different TensorFlow Lite model:

1. **Replace Model File**: 
   - Place your `.tflite` model file in `app/src/main/assets/models/`
   - Update the filename in `ImageClassifier.kt` if different from `mobilenet_v1_1.0_224.tflite`

2. **Update Labels** (if needed):
   - Replace `labels_mobilenet.txt` with your model's labels
   - Ensure the number of labels matches your model's output classes

3. **Adjust Input Size** (if needed):
   - Modify the input size in `ImageClassifier.preprocessImage()` if your model expects different dimensions
   - Common sizes: 224x224, 299x299, 512x512

4. **Update Classification Options**:
   - Modify `ImageClassifierOptions` in `ImageClassifier.initializeClassifier()` if needed
   - Adjust `setMaxResults()` based on your requirements

## Dependencies

- **TensorFlow Lite**: `org.tensorflow:tensorflow-lite-task-vision:0.4.4`
- **Jetpack Compose**: UI toolkit for modern Android development
- **Material 3**: Latest Material Design components
- **Coil**: Image loading library for Compose
- **Lifecycle**: ViewModel and LiveData/StateFlow support

## Permissions

The app requires the following permissions:
- `READ_EXTERNAL_STORAGE`: For accessing images on Android 12 and below
- `READ_MEDIA_IMAGES`: For accessing images on Android 13 and above

## Testing

The app has been designed to work on Android 6.0 (API 23) and above. Test on various devices to ensure compatibility.

## Troubleshooting

1. **Model Loading Issues**: Ensure the `.tflite` file is not compressed (handled by `aaptOptions.noCompress("tflite")`)
2. **Image Loading Issues**: Check that the app has proper permissions for image access
3. **Classification Errors**: Verify that the model file is compatible with TensorFlow Lite Task Vision library

## Future Enhancements

- Add support for camera capture
- Implement model quantization for smaller app size
- Add batch processing capabilities
- Implement custom model training integration
- Add confidence threshold filtering
