package com.palsaloid.storydicoding.di

import android.content.Context
import com.palsaloid.storydicoding.data.StoryRepository
import com.palsaloid.storydicoding.data.local.LocalDataSource
import com.palsaloid.storydicoding.data.local.room.StoryDatabase
import com.palsaloid.storydicoding.data.remote.RemoteDataSource
import com.palsaloid.storydicoding.data.remote.retrofit.ApiConfig
import com.palsaloid.storydicoding.domain.repository.IStoryRepository
import com.palsaloid.storydicoding.domain.usecase.StoryInteractor
import com.palsaloid.storydicoding.domain.usecase.StoryUseCase
import com.palsaloid.storydicoding.utils.AppExecutors

object Injection {

    private fun provideRepository(context: Context) : IStoryRepository {
        val database = StoryDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService())
        val localDataSource = LocalDataSource.getInstance(database.storyDao())
        val appExecutors = AppExecutors()
        return StoryRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideStoryUseCase(context: Context): StoryUseCase {
        val repository = provideRepository(context)
        return StoryInteractor(repository)
    }
}