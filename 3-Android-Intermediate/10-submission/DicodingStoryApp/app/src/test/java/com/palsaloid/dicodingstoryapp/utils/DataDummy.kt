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

    fun generateDummyToken() = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXpwcW5yLU85dlNTT0ZTR2oiLCJpYXQiOjE2ODI0MzM2NjV9.mK484Q1LUMX_eG7Rc4KoZeyUMD_1hgz0F1xocBq7Fvs"
}