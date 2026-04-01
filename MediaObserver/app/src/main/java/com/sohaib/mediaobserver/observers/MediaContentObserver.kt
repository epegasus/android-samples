package com.sohaib.mediaobserver.observers

import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore

class MediaContentObserver(
    private val contentResolver: ContentResolver,
    private val onChangeCallback: () -> Unit
) : ContentObserver(Handler(Looper.getMainLooper())) {

    private var lastTimeOfCall = 0L
    private var lastTimeOfUpdate = 0L
    private var thresholdTime: Long = 5000

    /**
     * Call the provided callback when a change is detected
     */
    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)


        lastTimeOfCall = System.currentTimeMillis()

        if (lastTimeOfCall - lastTimeOfUpdate > thresholdTime) {
            onChangeCallback.invoke()
            lastTimeOfUpdate = System.currentTimeMillis()
        }
    }

    fun register() {
        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, this)
    }

    fun unregister() {
        contentResolver.unregisterContentObserver(this)
    }
}