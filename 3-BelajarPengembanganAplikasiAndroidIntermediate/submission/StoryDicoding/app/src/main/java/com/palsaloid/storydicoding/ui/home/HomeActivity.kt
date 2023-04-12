package com.palsaloid.storydicoding.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.palsaloid.storydicoding.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}