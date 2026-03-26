package com.sohaib.navigationcomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sohaib.navigationcomponents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private fun initializations() {
        // Fetching Nav Controller in Activity
        val navHost = supportFragmentManager.findFragmentById(R.id.navHostContainer_Main) as NavHostFragment
        navController = navHost.navController

        // Creating Top Level Destinations
        appBarConfiguration = AppBarConfiguration(setOf(R.id.fragmentHome, R.id.fragmentSettings, R.id.fragmentNotifications))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializations()

        setupActionBarWithNavController(navController)
        binding.bottomNavigationViewMain.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}