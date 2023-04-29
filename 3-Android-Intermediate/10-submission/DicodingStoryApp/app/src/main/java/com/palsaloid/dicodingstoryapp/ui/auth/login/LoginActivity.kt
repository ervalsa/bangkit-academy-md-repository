package com.palsaloid.dicodingstoryapp.ui.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.palsaloid.dicodingstoryapp.MainActivity
import com.palsaloid.dicodingstoryapp.R
import com.palsaloid.dicodingstoryapp.data.Result
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.data.model.UserModel
import com.palsaloid.dicodingstoryapp.databinding.ActivityLoginBinding
import com.palsaloid.dicodingstoryapp.ui.auth.AuthViewModel
import com.palsaloid.dicodingstoryapp.utils.UserViewModel
import com.palsaloid.dicodingstoryapp.utils.UserViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUserViewModel()

        authViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        authViewModel.snackBarText.observe(this) { snackbar ->
            snackbar.getContentIfNotHandled()?.let {
                Snackbar.make(
                    window.decorView.rootView,
                    resources.getString(R.string.login_status_failed),
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
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        userViewModel.saveUser(
                            UserModel(
                                name = email,
                                token = result.data.loginResult.token,
                                isLogin = true
                            )
                        )

                        Toast.makeText(
                            this,
                            resources.getString(R.string.login_status_success),
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(
                UserPreference.getInstance(dataStore))
        )[UserViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}