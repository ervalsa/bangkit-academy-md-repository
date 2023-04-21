package com.palsaloid.storydicoding.ui.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.palsaloid.storydicoding.HomeViewModelFactory
import com.palsaloid.storydicoding.MainActivity
import com.palsaloid.storydicoding.data.local.datastore.UserPreference
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import com.palsaloid.storydicoding.databinding.ActivityLoginBinding
import com.palsaloid.storydicoding.domain.model.User
import com.palsaloid.storydicoding.ui.auth.AuthViewModel
import com.palsaloid.storydicoding.ui.auth.register.RegisterActivity
import com.palsaloid.storydicoding.utils.UserViewModel

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupUserViewModel()
        setupAuthViewModel()

        authViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        authViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let {
                Snackbar.make(
                    window.decorView.rootView,
                    "Login gagal, email atau password salah",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtInputEmail.text.toString()
            val password = binding.edtInputPassword.text.toString()

            authViewModel.login(email, password)
            authViewModel.loginResult.observe(this) { result ->
                when(result) {
                    is ApiResult.Empty -> {
                        Toast.makeText(
                            this,
                            "Login gagal, email atau password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ApiResult.Success -> {
                        userViewModel.saveUser(User(email, token = result.data.loginResult.token, isLogin = true))
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    is ApiResult.Error -> {
                        Toast.makeText(
                            this,
                            "Login gagal, email atau password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(
                UserPreference.getInstance(datastore))
        )[UserViewModel::class.java]
    }

    private fun setupAuthViewModel() {
        authViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[AuthViewModel::class.java]
    }
}