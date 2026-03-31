package com.sohaib.lifecycleobservers.lifecycle

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.sohaib.lifecycleobservers.databinding.ActivityLifecycleOwnerBinding
import com.sohaib.lifecycleobservers.dummy.ActivityDummy

class ActivityLifecycleOwner : AppCompatActivity() {

    private val binding by lazy { ActivityLifecycleOwnerBinding.inflate(layoutInflater) }
    private val managerObserver by lazy { ManagerObserver(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initObservers()

        lifecycle.addObserver(managerObserver)
        binding.mbDummyScreen.setOnClickListener { startActivity(Intent(this, ActivityDummy::class.java)) }
    }

    private fun initObservers() {
        val arrayList = arrayListOf<String>()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        binding.rvList.adapter = arrayAdapter
        managerObserver.liveData.observe(this) {
            arrayList.add(it)
            arrayAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(managerObserver)
    }
}