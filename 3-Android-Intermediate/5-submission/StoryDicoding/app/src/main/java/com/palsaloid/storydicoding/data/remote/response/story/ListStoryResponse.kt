package com.palsaloid.storydicoding.data.remote.response.story

import com.google.gson.annotations.SerializedName

data class ListStoryResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<StoryResponse>
)
