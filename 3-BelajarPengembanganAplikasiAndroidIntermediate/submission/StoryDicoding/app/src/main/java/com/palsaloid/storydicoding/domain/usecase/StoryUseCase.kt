package com.palsaloid.storydicoding.domain.usecase

import androidx.lifecycle.LiveData
import com.palsaloid.storydicoding.data.Result
import com.palsaloid.storydicoding.domain.model.Story

interface StoryUseCase {

    fun getAllStory(token: String): LiveData<Result<List<Story>>>
}