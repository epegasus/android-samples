package com.sohaib.lifecycleobservers.lifecycleEvent

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.sohaib.lifecycleobservers.databinding.ActivityLifecycleEventObserverBinding
import com.sohaib.lifecycleobservers.dummy.ActivityDummy

class ActivityLifecycleEventObserver : AppCompatActivity() {

    private val binding by lazy { ActivityLifecycleEventObserverBinding.inflate(layoutInflater) }
    private val lifeCycleEventObserver by lazy { LifeCycleEventObserver(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initObservers()

        lifecycle.addObserver(lifeCycleEventObserver)
        binding.mbDummyScreen.setOnClickListener { startActivity(Intent(this, ActivityDummy::class.java)) }
    }

    private fun initObservers() {
        val arrayList = arrayListOf<String>()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        binding.rvList.adapter = arrayAdapter
        lifeCycleEventObserver.liveData.observe(this) {
            arrayList.add(it)
            arrayAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifeCycleEventObserver)
    }
}