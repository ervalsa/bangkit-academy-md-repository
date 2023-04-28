package com.palsaloid.dicodingstoryapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.palsaloid.dicodingstoryapp.data.StoryRepository
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.di.Injection
import com.palsaloid.dicodingstoryapp.ui.home.HomeViewModel

class StoryViewModelFactory private constructor(
    private val storyRepository: StoryRepository,
    private val userPreference: UserPreference
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(storyRepository, userPreference) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: StoryViewModelFactory? = null

        fun getInstance(context: Context, userPreference: UserPreference): StoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: StoryViewModelFactory(
                    Injection.provideRepository(context), userPreference
                )
            }.also { instance = it }
    }
}