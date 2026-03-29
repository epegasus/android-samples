package com.sohaib.channels.coroutine

import android.util.Log
import com.sohaib.channels.utils.ConstantUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutineSuspend {

    fun initSuspendFunctions() {
        CoroutineScope(Dispatchers.Main).launch {
            impFirst()
            impSecond()
        }
    }

    private suspend fun impFirst() {
        Log.d(ConstantUtils.TAG_SUSPEND, "impFirst: started")
        delay(1000)
        Log.d(ConstantUtils.TAG_SUSPEND, "impFirst: completed")
    }

    private suspend fun impSecond() {
        Log.d(ConstantUtils.TAG_SUSPEND, "impSecond: started")
        delay(1000)
        Log.d(ConstantUtils.TAG_SUSPEND, "impSecond: completed")
    }
}