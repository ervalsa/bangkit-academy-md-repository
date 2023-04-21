package com.palsaloid.storydicoding.domain.repository

import androidx.lifecycle.LiveData
import com.palsaloid.storydicoding.data.Result
import com.palsaloid.storydicoding.domain.model.Story
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IStoryRepository {

    fun getAllStory(token: String): LiveData<Result<List<Story>>>

    fun addStory(token: String, imageFile: MultipartBody.Part, description: RequestBody)
}