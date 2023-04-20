package com.palsaloid.storydicoding.utils

import com.palsaloid.storydicoding.data.local.entity.StoryEntity
import com.palsaloid.storydicoding.data.remote.response.story.StoryResponse
import com.palsaloid.storydicoding.domain.model.Story

object DataMapper {

    fun mapResponsesToEntities(input: List<StoryResponse>): List<StoryEntity> {
        val storyList = ArrayList<StoryEntity>()

        input.map {
            val story = StoryEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                photoUrl = it.photoUrl,
                createdAt = it.createdAt,
                lat = it.lat,
                lon = it.lon
            )
            storyList.add(story)
        }

        return storyList
    }

    fun mapEntitiesToDomain(input: List<StoryEntity>): List<Story> =
        input.map {
            Story(
                id = it.id,
                name = it.name,
                description = it.description,
                photoUrl = it.photoUrl,
                createdAt = it.createdAt,
                lat = it.lat,
                lon = it.lon
            )
        }

    fun mapDomainToEntity(input: Story) = StoryEntity(
        id = input.id,
        name = input.name,
        description = input.description,
        photoUrl = input.photoUrl,
        createdAt = input.createdAt,
        lat = input.lat,
        lon = input.lon
    )
}