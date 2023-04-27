package com.palsaloid.storydicoding.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palsaloid.storydicoding.data.remote.response.story.DetailStoryResponse
import com.palsaloid.storydicoding.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _storyData = MutableLiveData<DetailStoryResponse>()
    val storyData: LiveData<DetailStoryResponse> = _storyData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadStoryData(token: String, id: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailStory("Bearer $token", id)
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(call: Call<DetailStoryResponse>, response: Response<DetailStoryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _storyData.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailViewModel", t.message.toString())
            }
        })
    }
}