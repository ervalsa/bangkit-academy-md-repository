package com.palsaloid.githubmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @field:SerializedName("userListResponse")
    val userListResponse: List<UserItem>
)
