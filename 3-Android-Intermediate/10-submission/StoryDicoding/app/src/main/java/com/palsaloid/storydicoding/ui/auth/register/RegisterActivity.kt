package com.palsaloid.storydicoding.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.palsaloid.storydicoding.data.Result
import com.palsaloid.storydicoding.databinding.ActivityRegisterBinding
import com.palsaloid.storydicoding.ui.auth.AuthViewModel
import com.palsaloid.storydicoding.ui.auth.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAuthViewModel()

        authViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.edtInputName.text.toString()
            val email = binding.edtInputEmail.text.toString()
            val password = binding.edtInputPassword.text.toString()

            authViewModel.register(name, email, password)
            authViewModel.registerResult.observe(this) { result ->
                when(result) {
                    is Result.Loading -> showLoading(true)

                    is Result.Success -> {
                        AlertDialog.Builder(this).apply {
                            setTitle("Selamat!")
                            setMessage("Akun anda sudah jadi, mari login dan buat cerita menarik.")
                            setPositiveButton("Lanjut") { _, _ ->
                                finish()
                            }
                            create()
                            show()
                        }
                    }

                    is Result.Error -> {
                        Toast.makeText(
                            this,
                            "Gagal membuat akun",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setupAuthViewModel() {
        authViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[AuthViewModel::class.java]
    }
}