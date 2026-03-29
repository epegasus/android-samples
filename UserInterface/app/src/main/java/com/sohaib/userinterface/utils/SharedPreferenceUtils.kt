package com.sohaib.userinterface.utils

import android.content.Context

/**
 * @Author: SOHAIB AHMED
 * @Date: 2/12/2024
 * @Accounts
 *      -> GitHub: https://github.com/epegasus
 *      -> Stack Overflow: https://stackoverflow.com/users/20440272/sohaib-ahmed
 */
class SharedPreferenceUtils(private val context: Context) {

    private val sharedPreferences by lazy { context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE) }
    private val darkMode = "darkMode"

    var isDarkMode: Boolean
        get() = sharedPreferences.getBoolean(darkMode, false)
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(darkMode, value)
                apply()
            }
        }
}