package com.sohaib.schedulebackgroundtask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sohaib.schedulebackgroundtask.alarm.ActivityAlarmManager
import com.sohaib.schedulebackgroundtask.broadcastReceiver.ActivityBroadcastReceiver
import com.sohaib.schedulebackgroundtask.databinding.ActivityMainBinding
import com.sohaib.schedulebackgroundtask.jobSchedule.ActivityJobSchedule

const val TAG = "MyTag"

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mbBroadcastReceiver.setOnClickListener { startActivity(Intent(this, ActivityBroadcastReceiver::class.java)) }
        binding.mbAlarm.setOnClickListener { startActivity(Intent(this, ActivityAlarmManager::class.java)) }
        binding.mbJobSchedule.setOnClickListener { startActivity(Intent(this, ActivityJobSchedule::class.java)) }
    }
}