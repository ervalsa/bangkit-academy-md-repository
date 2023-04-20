package com.palsaloid.storydicoding.ui.home

import androidx.lifecycle.ViewModel
import com.palsaloid.storydicoding.data.StoryRepository
import com.palsaloid.storydicoding.domain.usecase.StoryUseCase
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(private val storyUseCase: StoryUseCase) : ViewModel() {

    fun getAllStories(token: String) = storyUseCase.getAllStory(token)

//    fun addStory(token: String, imageFile: MultipartBody.Part, description: RequestBody) =
//        storyRepository.addStory(token, imageFile, description)
}