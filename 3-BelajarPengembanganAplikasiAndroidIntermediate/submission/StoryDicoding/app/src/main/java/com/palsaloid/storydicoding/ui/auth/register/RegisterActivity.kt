package com.palsaloid.storydicoding.ui.auth.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.palsaloid.storydicoding.R
import com.palsaloid.storydicoding.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}