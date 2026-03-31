package com.sohaib.lifecycleobservers.defaults

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
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

class DefaultManagerObserver(private val context: Context) : DefaultLifecycleObserver {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> get() = _liveData

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d(TAG, "onCreate: called")
        mediaPlayer = MediaPlayer.create(context, Uri.parse("dummyPath"))
        _liveData.value = "onCreate"
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(TAG, "onStart: called")
        _liveData.value = "onStart"
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d(TAG, "onResume: called")
        mediaPlayer?.start()
        _liveData.value = "onResume"
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d(TAG, "onPause: called")
        mediaPlayer?.pause()
        _liveData.value = "onPause"
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d(TAG, "onStop: called")
        _liveData.value = "onStop"
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d(TAG, "onDestroy: called")
        mediaPlayer?.release()
        mediaPlayer = null
        _liveData.value = "onDestroy"
    }
}