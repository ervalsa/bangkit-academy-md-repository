package com.palsaloid.storydicoding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.palsaloid.storydicoding.data.StoryRepository
import com.palsaloid.storydicoding.di.Injection
import com.palsaloid.storydicoding.domain.usecase.StoryUseCase
import com.palsaloid.storydicoding.ui.home.StoryViewModel

class StoryViewModelFactory(
    private val storyUseCase: StoryUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(storyUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryViewModelFactory? = null

        fun getInstance(context: Context): StoryViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoryViewModelFactory(Injection.provideStoryUseCase(context))
            }
    }
}