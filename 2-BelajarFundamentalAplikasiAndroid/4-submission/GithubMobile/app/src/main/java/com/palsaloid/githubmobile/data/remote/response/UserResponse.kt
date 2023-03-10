package com.palsaloid.githubmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("UserResponse")
    val userResponse: List<UserItem>
)