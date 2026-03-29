package com.sohaib.userinterface.managers

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.sohaib.userinterface.utils.SharedPreferenceUtils

/**
 * @Author: SOHAIB AHMED
 * @Date: 2/12/2024
 * @Accounts
 *      -> GitHub: https://github.com/epegasus
 *      -> Stack Overflow: https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

class UiModeManager(private val context: Context) {

    private val uiManager by lazy { context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager }
    private val sharedPreferenceUtils by lazy { SharedPreferenceUtils(context) }

    var recreateNeeded = false

    fun initTheme() {
        applyTheme(sharedPreferenceUtils.isDarkMode)
    }

    /**
     *  check if system's dark mode is on
     *
     *  @param context: Must be of fresh activity/fragment (in order to work properly)
     *  @return 'true' if the system's dark mode is on, 'false' otherwise.
     */
    fun isDarkMode(context: Context): Boolean {
        val uiMode = context.resources.configuration.uiMode
        return uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    fun applyTheme(checked: Boolean) {
        AppCompatDelegate.isCompatVectorFromResourcesEnabled()

        sharedPreferenceUtils.isDarkMode = checked
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            when (checked) {
                true -> uiManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_YES)
                false -> uiManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_NO)
            }
        } else {
            when (checked) {
                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}