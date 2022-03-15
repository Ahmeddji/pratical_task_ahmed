package com.logger.practicletask.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.logger.practicletask.R
import com.logger.practicletask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupNavigationWithDrawer()
    }

    private fun setupNavigationWithDrawer() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeNavHostFragment) as? NavHostFragment
        val navController = navHostFragment?.navController
        navController?.let { NavigationUI.setupWithNavController(binding.homeBottomNav, it) }
    }
}