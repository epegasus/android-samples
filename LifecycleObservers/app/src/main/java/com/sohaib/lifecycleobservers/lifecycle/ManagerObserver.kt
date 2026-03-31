package com.sohaib.lifecycleobservers.lifecycle

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.sohaib.lifecycleobservers.utils.GeneralUtils.TAG

/**
 * @Author: SOHAIB AHMED
 * @Date: 9/25/2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

class ManagerObserver(private val context: Context) : LifecycleObserver {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> get() = _liveData

    private var mediaPlayer: MediaPlayer? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.d(TAG, "onCreate: called")
        mediaPlayer = MediaPlayer.create(context, Uri.parse("dummyPath"))
        _liveData.value = "onCreate"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.d(TAG, "onStart: called")
        _liveData.value = "onStart"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(TAG, "onResume: called")
        mediaPlayer?.start()
        _liveData.value = "onResume"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG, "onPause: called")
        mediaPlayer?.pause()
        _liveData.value = "onPause"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.d(TAG, "onStop: called")
        _liveData.value = "onStop"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        mediaPlayer?.release()
        mediaPlayer = null
        _liveData.value = "onDestroy"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny() {
        Log.d(TAG, "onAny: called")
        _liveData.value = "onAny"
    }
}