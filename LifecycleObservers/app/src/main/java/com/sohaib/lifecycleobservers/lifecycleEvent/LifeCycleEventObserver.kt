package com.sohaib.lifecycleobservers.lifecycleEvent

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sohaib.lifecycleobservers.utils.GeneralUtils.TAG

/**
 * @Author: SOHAIB AHMED
 * @Date: 9/25/2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

class LifeCycleEventObserver(private val context: Context) : LifecycleEventObserver {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> get() = _liveData

    private var mediaPlayer: MediaPlayer? = null

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.d(TAG, "onCreate: called")
                mediaPlayer = MediaPlayer.create(context, Uri.parse("dummyPath"))
                _liveData.value = "onCreate"
            }

            Lifecycle.Event.ON_START -> {
                Log.d(TAG, "onStart: called")
                _liveData.value = "onStart"
            }

            Lifecycle.Event.ON_RESUME -> {
                Log.d(TAG, "onResume: called")
                mediaPlayer?.start()
                _liveData.value = "onResume"
            }

            Lifecycle.Event.ON_PAUSE -> {
                Log.d(TAG, "onPause: called")
                mediaPlayer?.pause()
                _liveData.value = "onPause"
            }

            Lifecycle.Event.ON_STOP -> {
                Log.d(TAG, "onStop: called")
                _liveData.value = "onStop"
            }

            Lifecycle.Event.ON_DESTROY -> {
                Log.d(TAG, "onDestroy: called")
                mediaPlayer?.release()
                mediaPlayer = null
                _liveData.value = "onDestroy"
            }

            Lifecycle.Event.ON_ANY -> {
                Log.d(TAG, "onAny: called")
                _liveData.value = "onAny"
            }
        }
    }
}