package com.sohaib.viewstub

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sohaib.viewstub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /* binding.viewStub1.inflate()
         binding.viewStub2.inflate()
         binding.viewStub3.inflate()
         binding.viewStub4.inflate()*/

        withDelay(1000) { binding.viewStub1.inflate() }
        withDelay(2000) { binding.viewStub2.inflate() }
        withDelay(3000) { binding.viewStub3.inflate() }
        withDelay(4000) { binding.viewStub4.inflate() }

        /* binding.viewStub3.setOnInflateListener { viewStub, view ->
             binding.viewStub4.inflate()
         }

         binding.viewStub2.setOnInflateListener { viewStub, view ->
             binding.viewStub3.inflate()
         }

         binding.viewStub1.setOnInflateListener { p0, p1 ->
             binding.viewStub2.inflate()
         }

         binding.viewStub1.inflate()*/
    }

    private fun withDelay(delay: Long = 300, block: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(block, delay)
    }
}