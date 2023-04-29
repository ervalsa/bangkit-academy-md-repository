package com.palsaloid.dicodingstoryapp.ui.auth.register

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.palsaloid.dicodingstoryapp.R
import com.palsaloid.dicodingstoryapp.data.Result
import com.palsaloid.dicodingstoryapp.databinding.ActivityRegisterBinding
import com.palsaloid.dicodingstoryapp.ui.auth.AuthViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                            setTitle(resources.getString(R.string.alert_title))
                            setMessage(resources.getString(R.string.alert_message))
                            setPositiveButton(resources.getString(R.string.alert_positive_button)) { _, _ ->
                                finish()
                            }
                            create()
                            show()
                        }
                    }

                    is Result.Error -> {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.register_status_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}