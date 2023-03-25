package com.palsaloid.githubmobile

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.palsaloid.githubmobile.databinding.ActivityMainBinding
import com.palsaloid.githubmobile.ui.setting.SettingPreference
import com.palsaloid.githubmobile.ui.setting.SettingViewModel
import com.palsaloid.githubmobile.utils.SettingViewModelFactory

class MainActivity : AppCompatActivity() {

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

    companion object {
        lateinit var binding: ActivityMainBinding
    }
}