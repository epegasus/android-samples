package com.sohaib.zoommeeting

import android.content.Intent
import com.sohaib.zoommeeting.databinding.ActivityMainBinding
import com.sohaib.zoommeeting.oneToOne.presentation.ui.ActivityClient
import com.sohaib.zoommeeting.oneToOne.presentation.ui.ActivityHost
import com.sohaib.zoommeeting.utilities.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreated() {
        binding.mbStartMeetingMain.setOnClickListener { startActivity(Intent(this, ActivityHost::class.java)) }
        binding.mbJoinMeetingMain.setOnClickListener { startActivity(Intent(this, ActivityClient::class.java)) }
    }
}