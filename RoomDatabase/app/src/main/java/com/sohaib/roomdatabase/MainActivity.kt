package com.sohaib.roomdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.sohaib.roomdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private fun initializations() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_container_Main) as NavHostFragment
        navController = navHost.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.fragmentHome))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializations()
        setUI()
    }

    private fun setUI() {
        binding.toolbarMain.setupWithNavController(navController, appBarConfiguration)
    }
}