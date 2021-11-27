package com.example.finalplayground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // With androidx.fragment.app.FragmentContainerView being navHostFragment, the initialisation has
        // to be done in following manner by using findFragmentById

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return

        navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBar(appBarConfiguration)
    }

    private fun setupActionBar(appBarConfig: AppBarConfiguration) {
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
    }

    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration)
}
