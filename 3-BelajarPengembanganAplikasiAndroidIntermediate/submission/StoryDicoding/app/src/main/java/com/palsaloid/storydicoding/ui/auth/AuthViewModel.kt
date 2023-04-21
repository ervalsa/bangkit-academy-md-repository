package com.palsaloid.storydicoding.ui.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palsaloid.storydicoding.data.Result
import com.palsaloid.storydicoding.data.remote.response.auth.LoginResponse
import com.palsaloid.storydicoding.data.remote.response.auth.RegisterResponse
import com.palsaloid.storydicoding.data.remote.retrofit.ApiConfig
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import com.palsaloid.storydicoding.data.remote.retrofit.ApiService
import com.palsaloid.storydicoding.domain.usecase.StoryUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<ApiResult<LoginResponse>>()
    val loginResult: LiveData<ApiResult<LoginResponse>> = _loginResult

    private val _registerResult = MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                _loginResult.value =
                    if (response.isSuccessful && responseBody != null) {
                        ApiResult.Success(responseBody)
                    } else {
                        ApiResult.Empty
                    }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _loginResult.value = ApiResult.Error(t.message.toString())
                Log.e("AuthViewModel", t.message.toString())
            }
        })
    }

    fun register(name: String, email:String, password: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false

                val responseBody = response.body()
                _registerResult.value =
                    if (response.isSuccessful && responseBody != null) {
                        Result.Success(responseBody)
                    } else {
                        Result.Error(response.message())
                    }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _registerResult.value = Result.Error(t.message.toString())
                Log.e("AuthViewModel", t.message.toString())
            }

        })
    }
}