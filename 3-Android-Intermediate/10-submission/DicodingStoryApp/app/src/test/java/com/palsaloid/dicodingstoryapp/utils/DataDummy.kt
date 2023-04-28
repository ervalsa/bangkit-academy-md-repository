package com.palsaloid.dicodingstoryapp.utils

import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<StoryItem> {
        val items: MutableList<StoryItem> = arrayListOf()

        for (i in 0..100) {
            val story = StoryItem(
                i.toString(),
                name = "",
                description = "",
                photoUrl = "",
                createdAt = "",
                lat = 0.0.toFloat(),
                lon = 0.0.toFloat()
            )
            items.add(story)
        }

        return items
    }
}