package com.sohaib.flow

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sohaib.flow.callbackFlow.CallbackFlowActivity
import com.sohaib.flow.flow.FlowActivity

const val TAG = "MyTag"

class MainActivity : AppCompatActivity() {

    private val mbFlow by lazy { findViewById<TextView>(R.id.mbFlow) }
    private val mbCallbackFlow by lazy { findViewById<TextView>(R.id.mbCallbackFlow) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mbFlow.setOnClickListener { startActivity(Intent(this, FlowActivity::class.java)) }
        mbCallbackFlow.setOnClickListener { startActivity(Intent(this, CallbackFlowActivity::class.java)) }
    }
}