package com.palsaloid.dicodingstoryapp.ui.add_story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palsaloid.dicodingstoryapp.data.remote.response.story.FileUploadResponse
import com.palsaloid.dicodingstoryapp.data.remote.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.palsaloid.dicodingstoryapp.data.Result

class AddStoryViewModel : ViewModel() {

    private val _addStoryResult = MutableLiveData<Result<FileUploadResponse>>()
    val addStoryResult: LiveData<Result<FileUploadResponse>> = _addStoryResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addStory(
        token: String,
        photo: MultipartBody.Part,
        description: RequestBody,
        lat: Float?,
        lon: Float?
    ) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().addStory("Bearer $token",
            photo, description, lat, lon
        )
        client.enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _addStoryResult.value = Result.Success(responseBody)
                    }
                } else {
                    _addStoryResult.value = Result.Error(response.message())
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                _isLoading.value = false
                _addStoryResult.value = Result.Error(t.message.toString())
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "AddStoryViewModel"
    }
}