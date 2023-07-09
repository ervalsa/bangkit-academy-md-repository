package com.palsaloid.storydicoding.ui.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palsaloid.storydicoding.data.remote.response.story.ListStoryResponse
import com.palsaloid.storydicoding.data.remote.response.story.StoryResponse
import com.palsaloid.storydicoding.data.remote.retrofit.ApiConfig
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {

    private val _mapResult = MutableLiveData<List<StoryResponse>>()
    val mapResult: LiveData<List<StoryResponse>> = _mapResult

    fun getAllStoryWithLocation(token: String) {
        val client = ApiConfig.getApiService().getStoryWithLocation("Bearer $token")
        client.enqueue(object: Callback<ListStoryResponse> {
            override fun onResponse(
                call: Call<ListStoryResponse>,
                response: Response<ListStoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _mapResult.value = response.body()?.listStory
                    }
                } else {
                    Log.e("MapsViewModel", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListStoryResponse>, t: Throwable) {
                Log.e("MapsViewModel", "onFailure : ${t.message.toString()}")
            }
        })
    }
}