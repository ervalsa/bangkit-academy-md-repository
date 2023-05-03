package com.palsaloid.dicodingstoryapp.di

import android.content.Context
import com.palsaloid.dicodingstoryapp.data.StoryRepository
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.data.local.room.StoryDatabase
import com.palsaloid.dicodingstoryapp.data.remote.retrofit.ApiConfig

object Injection {

    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val database = StoryDatabase.getInstance(context)

        return StoryRepository.getInstance(apiService, database)
    }
}