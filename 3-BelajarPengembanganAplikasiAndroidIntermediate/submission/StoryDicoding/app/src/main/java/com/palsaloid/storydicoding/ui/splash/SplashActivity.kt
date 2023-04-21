package com.palsaloid.storydicoding.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.palsaloid.storydicoding.HomeViewModelFactory
import com.palsaloid.storydicoding.MainActivity
import com.palsaloid.storydicoding.data.local.datastore.UserPreference
import com.palsaloid.storydicoding.databinding.ActivitySplashBinding
import com.palsaloid.storydicoding.ui.auth.login.LoginActivity
import com.palsaloid.storydicoding.utils.UserViewModel

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupViewModel()

        val handler = Handler()
        handler.postDelayed({
            isLogin()
        }, TIMER)
    }

    private fun setupViewModel() {
        userViewModel =
            ViewModelProvider(
                this,
                HomeViewModelFactory(UserPreference.getInstance(datastore))
            )[UserViewModel::class.java]
    }

    private fun isLogin() {
        userViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    companion object {
        const val TIMER = 2000L
    }
}