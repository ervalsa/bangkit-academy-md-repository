package com.palsaloid.storydicoding.ui.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palsaloid.storydicoding.data.remote.response.story.ListStoryResponse
import com.palsaloid.storydicoding.data.remote.retrofit.ApiConfig
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {

    private val _mapResult = MutableLiveData<ApiResult<ListStoryResponse>>()
    val mapResult: LiveData<ApiResult<ListStoryResponse>> = _mapResult

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
                        _mapResult.value = ApiResult.Success(responseBody)
                    }
                } else {
                    _mapResult.value = ApiResult.Error(response.message())
                    Log.e("MapsViewModel", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListStoryResponse>, t: Throwable) {
                _mapResult.value = ApiResult.Error(t.message.toString())
                Log.e("MapsViewModel", "onFailure : ${t.message.toString()}")
            }
        })
    }
}