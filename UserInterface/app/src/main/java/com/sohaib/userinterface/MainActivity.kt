package com.sohaib.userinterface

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.sohaib.userinterface.dataProvider.DpTypes
import com.sohaib.userinterface.databinding.ActivityMainBinding
import com.sohaib.userinterface.managers.ThemeManager
import com.sohaib.userinterface.managers.UiModeManager
import com.sohaib.userinterface.utils.ConstantUtils.type

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val themeManager by lazy { ThemeManager() }
    private val uiModeManager by lazy { UiModeManager(this) }

    private val dpTypes by lazy { DpTypes() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyTheme()
        fullScreen()
        onCreated()
    }

    /**
     *  Apply Dynamic Colors (from Material-3)
     *  Note:   App's theme must be of Material3
     */
    private fun applyTheme() {
        uiModeManager.initTheme()
        themeManager.applyTheme(this)
    }

    /**
     *  Apply Full Screen
     */
    private fun fullScreen() {
        themeManager.fullScreen(this, binding.root, type)
    }

    /**
     *  Your Code...
     */
    private fun onCreated() {
        setUI()

        binding.msUiMode.setOnCheckedChangeListener { _, isChecked -> applyThemeMode(isChecked) }
        binding.mbApply.setOnClickListener { onApplyClick() }
    }

    private fun setUI() {
        binding.msUiMode.isChecked = uiModeManager.isDarkMode(this)

        // fill drop-down menu
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dpTypes.list)
        binding.actList.setAdapter(arrayAdapter)
        binding.actList.setText(dpTypes.list[type], false)
        binding.actList.setOnItemClickListener { _, _, position, _ ->
            type = position
        }
    }

    private fun applyThemeMode(isChecked: Boolean) {
        uiModeManager.recreateNeeded = true
        uiModeManager.applyTheme(isChecked)
    }

    private fun onApplyClick() {
        fullScreen()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}