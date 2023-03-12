package com.palsaloid.githubmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.palsaloid.githubmobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val bottomNavView: BottomNavigationView = binding.bottomNav
        val navController = findNavController(R.id.nav_host_fragment_container)
        bottomNavView.itemIconTintList = null
        bottomNavView.setupWithNavController(navController)
    }
}