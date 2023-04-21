package com.palsaloid.storydicoding.domain.usecase

import android.content.Context
import com.palsaloid.storydicoding.domain.repository.IStoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody


class StoryInteractor(private val storyRepository: IStoryRepository) : StoryUseCase {

    override fun getAllStory(token: String) =
        storyRepository.getAllStory(token)

    override fun addStory(token: String, imageFile: MultipartBody.Part, description: RequestBody) {
        storyRepository.addStory(token, imageFile, description)
    }


}