package com.palsaloid.githubmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserItem(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String ? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("bio")
    val bio: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("public_repos")
    val public_repos: Int? = null,

    @field:SerializedName("public_gists")
    val public_gists: Int? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("following")
    val following: Int? = null
)
