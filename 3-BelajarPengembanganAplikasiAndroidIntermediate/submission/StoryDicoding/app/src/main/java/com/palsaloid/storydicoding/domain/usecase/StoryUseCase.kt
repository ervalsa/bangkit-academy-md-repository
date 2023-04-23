package com.palsaloid.storydicoding.domain.usecase

import android.content.Context
import androidx.lifecycle.LiveData
import com.palsaloid.storydicoding.data.Result
import com.palsaloid.storydicoding.domain.model.Story
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoryUseCase {

    fun getAllStory(token: String): LiveData<Result<List<Story>>>
}