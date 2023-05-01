package com.palsaloid.dicodingstoryapp.ui.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryItem
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryResponse
import com.palsaloid.dicodingstoryapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {

    private val _mapResult = MutableLiveData<List<StoryItem>>()
    val mapResult: LiveData<List<StoryItem>> = _mapResult

    fun getStoriesLocation(token: String) {
        val client = ApiConfig.getApiService().getStoryLocation("Bearer $token")
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _mapResult.value = response.body()?.listStory
                    }
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "MapsViewModel"
    }
}