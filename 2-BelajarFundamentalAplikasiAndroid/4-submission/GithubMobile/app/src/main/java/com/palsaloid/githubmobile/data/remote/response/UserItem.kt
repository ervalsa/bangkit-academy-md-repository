package com.palsaloid.githubmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("bio")
    val bio: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("public_repos")
    val public_repos: Int,

    @field:SerializedName("public_gists")
    val public_gists: Int,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int
)
