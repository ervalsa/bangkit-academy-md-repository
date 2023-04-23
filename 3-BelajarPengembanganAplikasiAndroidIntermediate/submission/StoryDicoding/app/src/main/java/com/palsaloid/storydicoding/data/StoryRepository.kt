package com.palsaloid.storydicoding.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.palsaloid.storydicoding.data.local.LocalDataSource
import com.palsaloid.storydicoding.data.remote.RemoteDataSource
import com.palsaloid.storydicoding.data.remote.response.story.FileUploadResponse
import com.palsaloid.storydicoding.data.remote.response.story.StoryResponse
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import com.palsaloid.storydicoding.domain.model.Story
import com.palsaloid.storydicoding.domain.repository.IStoryRepository
import com.palsaloid.storydicoding.utils.AppExecutors
import com.palsaloid.storydicoding.utils.DataMapper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class StoryRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IStoryRepository {

    override fun getAllStory(token: String): LiveData<Result<List<Story>>> =
        object : NetworkBoundResource<List<Story>, List<StoryResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Story>> {
                return localDataSource.getAllStory().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Story>?): Boolean = true

            override fun createCall(): LiveData<ApiResult<List<StoryResponse>>> =
                remoteDataSource.getAllStory(token)

            override fun saveCallResult(data: List<StoryResponse>) {
                val storyList = DataMapper.mapResponsesToEntities(data)
                localDataSource.deleteStory()
                localDataSource.insertStory(storyList)
            }
        }.asLiveData()

    companion object {
        @Volatile
        private var INSTANCE: StoryRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): StoryRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoryRepository(remoteData, localData, appExecutors)
            }
    }
}