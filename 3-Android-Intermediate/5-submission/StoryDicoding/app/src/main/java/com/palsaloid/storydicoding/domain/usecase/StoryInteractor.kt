package com.palsaloid.storydicoding.domain.usecase

import com.palsaloid.storydicoding.domain.repository.IStoryRepository

class StoryInteractor(private val storyRepository: IStoryRepository) : StoryUseCase {

    override fun getAllStory(token: String) =
        storyRepository.getAllStory(token)
}