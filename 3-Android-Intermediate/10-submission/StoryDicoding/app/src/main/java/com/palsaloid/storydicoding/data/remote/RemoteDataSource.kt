package com.palsaloid.storydicoding.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.palsaloid.storydicoding.data.remote.response.story.ListStoryResponse
import com.palsaloid.storydicoding.data.remote.response.story.StoryResponse
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import com.palsaloid.storydicoding.data.remote.retrofit.ApiService
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

    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteDataSource(service)
            }
    }
}