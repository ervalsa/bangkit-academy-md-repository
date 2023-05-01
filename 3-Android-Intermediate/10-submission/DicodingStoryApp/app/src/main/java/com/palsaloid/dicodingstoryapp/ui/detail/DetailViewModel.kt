package com.palsaloid.dicodingstoryapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palsaloid.dicodingstoryapp.data.remote.response.story.DetailStoryResponse
import com.palsaloid.dicodingstoryapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.palsaloid.dicodingstoryapp.data.Result

class DetailViewModel : ViewModel() {

    private val _storyResult = MutableLiveData<Result<DetailStoryResponse>>()
    val storyResult: LiveData<Result<DetailStoryResponse>> = _storyResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun detailStory(token: String, id: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailStory("Bearer $token", id)
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _storyResult.value = Result.Success(responseBody)
                    }
                } else {
                    _storyResult.value = Result.Error(response.message())
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                _isLoading.value = false
                _storyResult.value = Result.Error(t.message.toString())
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}