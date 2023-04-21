package com.palsaloid.storydicoding.data.remote

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.palsaloid.storydicoding.MainActivity
import com.palsaloid.storydicoding.data.Result
import com.palsaloid.storydicoding.data.remote.response.auth.LoginResponse
import com.palsaloid.storydicoding.data.remote.response.auth.RegisterResponse
import com.palsaloid.storydicoding.data.remote.response.story.FileUploadResponse
import com.palsaloid.storydicoding.data.remote.response.story.ListStoryResponse
import com.palsaloid.storydicoding.data.remote.response.story.StoryResponse
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import com.palsaloid.storydicoding.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {

    fun getAllStory(token: String): LiveData<ApiResult<List<StoryResponse>>> {
        val resultData = MutableLiveData<ApiResult<List<StoryResponse>>>()

        val client = apiService.getAllStory("Bearer $token", null, null)
        client.enqueue(object : Callback<ListStoryResponse> {
            override fun onResponse(
                call: Call<ListStoryResponse>,
                response: Response<ListStoryResponse>
            ) {
                val dataArray = response.body()?.listStory
                resultData.value =
                    if (dataArray != null) ApiResult.Success(dataArray) else ApiResult.Empty
            }

            override fun onFailure(call: Call<ListStoryResponse>, t: Throwable) {
                resultData.value = ApiResult.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }

        })

        return resultData
    }

    fun addStory(token: String, imageFile: MultipartBody.Part, description: RequestBody) {
        val client = apiService.addStory("Bearer $token", imageFile, description)
        client.enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (!responseBody.error) {
                            Log.e("RemoteDataSource", response.message())
                        }
                    } else {
                        Log.e("RemoteDataSource", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
    }

    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteDataSource(service)
            }
    }
}