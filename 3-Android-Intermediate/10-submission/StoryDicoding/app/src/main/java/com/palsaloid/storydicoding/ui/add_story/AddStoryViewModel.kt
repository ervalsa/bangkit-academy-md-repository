package com.palsaloid.storydicoding.ui.add_story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palsaloid.storydicoding.data.remote.response.story.FileUploadResponse
import com.palsaloid.storydicoding.data.remote.retrofit.ApiConfig
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryViewModel : ViewModel() {

    private val _addResult = MutableLiveData<ApiResult<FileUploadResponse>>()
    val addResult: LiveData<ApiResult<FileUploadResponse>> = _addResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addStory(token: String, photo: MultipartBody.Part, description: RequestBody) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().addStory("Bearer $token", photo, description)
        client.enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _addResult.value = ApiResult.Success(responseBody)
                    }
                } else {
                    _addResult.value = ApiResult.Error(response.message())
                    Log.e("AddStoryViewModel", "onFailuer: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                _isLoading.value = false
                _addResult.value = ApiResult.Error(t.message.toString())
                Log.e("AddStoryViewModel", t.message.toString())
            }
        })
    }
}