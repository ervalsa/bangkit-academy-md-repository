package com.palsaloid.githubmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @field:SerializedName("items")
    val userListResponse: List<UserResponse>
)