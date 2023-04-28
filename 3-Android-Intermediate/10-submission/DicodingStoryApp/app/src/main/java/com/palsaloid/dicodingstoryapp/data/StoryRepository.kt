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

//    fun getStories(token: String) : LiveData<Result<List<StoryItem>>> = liveData {
//        emit(Result.Loading)
//
//        try {
//            val response = apiService.getStories("Bearer $token")
//            val stories = response.listStory
//            val storyList = stories.map { story ->
//                StoryItem(
//                    story.id,
//                    story.name,
//                    story.description,
//                    story.photoUrl,
//                    story.createdAt,
//                    story.lat,
//                    story.lon
//                )
//            }
//            storyDao.deleteAll()
//            storyDao.insertStory(storyList)
//        } catch (e: Exception) {
//            Log.d(TAG, "getStories: ${e.message.toString()}")
//            emit(Result.Error(e.message.toString()))
//        }
//
//        val localData: LiveData<Result<List<StoryItem>>> =
//            storyDao.getStories().map { Result.Success(it) }
//        emitSource(localData)
//    }

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