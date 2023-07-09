package com.palsaloid.storydicoding.domain.repository

import androidx.lifecycle.LiveData
import com.palsaloid.storydicoding.data.Result
import com.palsaloid.storydicoding.domain.model.Story

interface IStoryRepository {

    fun getAllStory(token: String): LiveData<Result<List<Story>>>
}