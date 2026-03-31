package com.sohaib.lifecycleobservers.defaults

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.sohaib.lifecycleobservers.databinding.ActivityDefaultLifeCycleObserverBinding
import com.sohaib.lifecycleobservers.dummy.ActivityDummy

class ActivityDefaultLifeCycleObserver : AppCompatActivity() {

    private val binding by lazy { ActivityDefaultLifeCycleObserverBinding.inflate(layoutInflater) }
    private val defaultManagerObserver by lazy { DefaultManagerObserver(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initObservers()

        lifecycle.addObserver(defaultManagerObserver)
        binding.mbDummyScreen.setOnClickListener { startActivity(Intent(this, ActivityDummy::class.java)) }
    }

    private fun initObservers() {
        val arrayList = arrayListOf<String>()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        binding.rvList.adapter = arrayAdapter
        defaultManagerObserver.liveData.observe(this) {
            arrayList.add(it)
            arrayAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(defaultManagerObserver)
    }
}