package com.sohaib.channels

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sohaib.channels.coroutine.CoroutineSuspend
import com.sohaib.channels.utils.ConstantUtils.TAG_CHANNEL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val taskChannel = Channel<Job>(capacity = Channel.UNLIMITED)
    private val myScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Way # 1
        execute()

        // Way # 2
        CoroutineSuspend().initSuspendFunctions()
    }

    private fun execute() {
        addTask("Task # 1")
        addTask("Task # 2")
        addTask("Task # 3")
        addTask("Task # 4")

        myScope.launch {
            taskChannel.consumeEach {
                it.join()
            }
        }
    }

    private fun addTask(taskName: String) {
        taskChannel.trySend(
            myScope.launch(start = CoroutineStart.LAZY) {
                Log.d(TAG_CHANNEL, "$taskName: called")
                delay(1000)
                Log.i(TAG_CHANNEL, "$taskName: completed")
            }
        )
    }
}