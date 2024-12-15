package com.lazuka.emotioncalendar.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.lazuka.emotioncalendar.R
import com.lazuka.emotioncalendar.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavView.setupWithNavController(navController)

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.eventsFragment -> {
                    val options = NavOptions.Builder()
                        .setPopUpTo(R.id.eventsFragment, true)
                        .setLaunchSingleTop(true)
                        .build()
                    navController.navigate(R.id.eventsFragment, null, options)

                    return@setOnItemSelectedListener true
                }

                else -> NavigationUI.onNavDestinationSelected(item, navController)
            }
        }
    }
}