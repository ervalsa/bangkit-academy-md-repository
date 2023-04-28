package com.palsaloid.dicodingstoryapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.palsaloid.dicodingstoryapp.data.local.room.StoryDao
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryItem
import com.palsaloid.dicodingstoryapp.data.remote.retrofit.ApiService

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val storyDao: StoryDao
) {

    fun getStories(token: String) : LiveData<Result<List<StoryItem>>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getStories("Bearer $token")
            val stories = response.listStory
            val storyList = stories.map { story ->
                StoryItem(
                    story.id,
                    story.name,
                    story.description,
                    story.photoUrl,
                    story.createdAt,
                    story.lat,
                    story.lon
                )
            }
            storyDao.deleteAll()
            storyDao.insertStory(storyList)
        } catch (e: Exception) {
            Log.d(TAG, "getStories: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

        val localData: LiveData<Result<List<StoryItem>>> =
            storyDao.getStories().map { Result.Success(it) }
        emitSource(localData)
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        private val TAG = "StoryRepository"

        fun getInstance(apiService: ApiService, storyDao: StoryDao): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, storyDao)
            }.also { instance = it }
    }
}