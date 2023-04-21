package com.palsaloid.storydicoding.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.palsaloid.storydicoding.MainActivity
import com.palsaloid.storydicoding.R
import com.palsaloid.storydicoding.StoryViewModelFactory
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import com.palsaloid.storydicoding.databinding.ActivityLoginBinding
import com.palsaloid.storydicoding.ui.auth.AuthViewModel
import com.palsaloid.storydicoding.ui.auth.register.RegisterActivity
import com.palsaloid.storydicoding.utils.StoryViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupViewModel()

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_login -> {
                val email = binding.edtInputEmail.text.toString()
                val password = binding.edtInputPassword.text.toString()

                authViewModel.login(email, password)
                authViewModel.loginResult.observe(this) {result ->
                    when(result) {
                        is ApiResult.Success -> {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is ApiResult.Error -> {

                        }

                        is ApiResult.Empty -> {

                        }
                    }
                }
            }

            R.id.btn_register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupViewModel() {
        authViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[AuthViewModel::class.java]
    }
}