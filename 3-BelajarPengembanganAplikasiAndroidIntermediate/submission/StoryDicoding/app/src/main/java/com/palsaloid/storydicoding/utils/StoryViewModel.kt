package com.palsaloid.storydicoding.utils

import androidx.lifecycle.ViewModel
import com.palsaloid.storydicoding.domain.usecase.StoryUseCase

class StoryViewModel(private val storyUseCase: StoryUseCase) : ViewModel() {

    fun getAllStories(token: String) = storyUseCase.getAllStory(token)
}