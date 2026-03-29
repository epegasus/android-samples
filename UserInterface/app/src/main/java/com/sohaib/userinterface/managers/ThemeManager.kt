package com.sohaib.userinterface.managers

import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.color.DynamicColors
import com.sohaib.userinterface.R
import com.sohaib.userinterface.utils.ConstantUtils.navBarHeight
import com.sohaib.userinterface.utils.ConstantUtils.statusBarHeight

/**
 * @Author: SOHAIB AHMED
 * @Date: 2/12/2024
 * @Accounts
 *      -> GitHub: https://github.com/epegasus
 *      -> Stack Overflow: https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

class ThemeManager {

    fun applyTheme(activity: AppCompatActivity) {
        DynamicColors.applyToActivityIfAvailable(activity)
    }

    /**
     * @param type  0: System Bar,  1: Status Bar,  2: Navigation Bar
     */

    fun fullScreen(activity: AppCompatActivity, view: View, type: Int) {
        activity.apply {
            enableEdgeToEdge()
            setContentView(view)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

                // Devices without rounded edge corners
                if (windowInsets.displayCutout == null) {
                    when (type) {
                        1 -> view.setPadding(insets.left, 0, insets.right, insets.bottom)
                        2 -> view.setPadding(insets.left, insets.top, insets.right, 0)
                        else -> view.setPadding(insets.left, 0, insets.right, 0)
                    }
                    return@setOnApplyWindowInsetsListener WindowInsetsCompat.CONSUMED
                }

                if (insets.top != 0) statusBarHeight = insets.top
                if (insets.bottom != 0) navBarHeight = insets.bottom
                view.setPadding(insets.left, statusBarHeight, insets.right, navBarHeight)

                WindowInsetsCompat.CONSUMED
            }
            WindowInsetsControllerCompat(window, window.decorView).apply {

                when (type) {
                    1 -> hide(WindowInsetsCompat.Type.statusBars())
                    2 -> hide(WindowInsetsCompat.Type.navigationBars())
                    else -> hide(WindowInsetsCompat.Type.systemBars())
                }

                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}