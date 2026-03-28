package com.sohaib.datastore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.DynamicColors
import com.sohaib.datastore.databinding.ActivityMainBinding
import com.sohaib.datastore.preferences.PreferencesActivity
import com.sohaib.datastore.proto.ProtoActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyToActivitiesIfAvailable(application)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mbPreference.setOnClickListener { startActivity(Intent(this, PreferencesActivity::class.java)) }
        binding.mbProto.setOnClickListener { startActivity(Intent(this, ProtoActivity::class.java)) }
    }
}