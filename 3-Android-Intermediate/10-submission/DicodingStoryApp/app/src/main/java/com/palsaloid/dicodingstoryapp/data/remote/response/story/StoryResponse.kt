package com.palsaloid.dicodingstoryapp.data.remote.response.story

import com.google.gson.annotations.SerializedName

data class StoryResponse (
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<StoryItem>
)