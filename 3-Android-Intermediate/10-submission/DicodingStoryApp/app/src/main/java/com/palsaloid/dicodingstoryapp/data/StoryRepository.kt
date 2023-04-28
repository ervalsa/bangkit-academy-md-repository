package com.palsaloid.dicodingstoryapp.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.palsaloid.dicodingstoryapp.data.local.room.StoryDatabase
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryItem
import com.palsaloid.dicodingstoryapp.data.remote.retrofit.ApiService

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getStories(token: String) : LiveData<PagingData<StoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getStories()
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        private val TAG = "StoryRepository"

        fun getInstance(apiService: ApiService, storyDatabase: StoryDatabase): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, storyDatabase)
            }.also { instance = it }
    }
}