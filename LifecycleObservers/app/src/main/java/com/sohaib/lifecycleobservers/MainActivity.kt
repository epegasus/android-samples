package com.sohaib.lifecycleobservers

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sohaib.lifecycleobservers.databinding.ActivityMainBinding
import com.sohaib.lifecycleobservers.defaults.ActivityDefaultLifeCycleObserver
import com.sohaib.lifecycleobservers.lifecycle.ActivityLifecycleOwner
import com.sohaib.lifecycleobservers.lifecycleEvent.ActivityLifecycleEventObserver

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mbLifecycleObserver.setOnClickListener { startActivity(Intent(this, ActivityLifecycleOwner::class.java)) }
        binding.mbDefaultObserver.setOnClickListener { startActivity(Intent(this, ActivityDefaultLifeCycleObserver::class.java)) }
        binding.mbLifecycleEventObserver.setOnClickListener { startActivity(Intent(this, ActivityLifecycleEventObserver::class.java)) }
    }
}