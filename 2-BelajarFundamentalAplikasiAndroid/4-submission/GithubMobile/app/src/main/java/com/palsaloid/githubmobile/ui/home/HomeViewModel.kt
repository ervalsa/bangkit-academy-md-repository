package com.palsaloid.githubmobile.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palsaloid.githubmobile.data.remote.response.UserListResponse
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import com.palsaloid.githubmobile.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<UserResponse>>()
    val listUser: LiveData<List<UserResponse>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadListUser()
    }

    private fun loadListUser() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllUsers()
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun searchUsername(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object : Callback<UserListResponse> {
            override fun onResponse(call: Call<UserListResponse>, response: Response<UserListResponse>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = response.body()?.userListResponse
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}