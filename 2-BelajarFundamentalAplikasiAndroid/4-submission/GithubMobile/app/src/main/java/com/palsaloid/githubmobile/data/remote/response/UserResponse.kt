package com.palsaloid.githubmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("login")
    var login: String? = null,

    @field:SerializedName("avatar_url")
    var avatarUrl: String ? = null,

    @field:SerializedName("bio")
    var bio: String? = null,

    @field:SerializedName("company")
    var company: String? = null,

    @field:SerializedName("location")
    var location: String? = null,

    @field:SerializedName("public_repos")
    var public_repos: Int? = null,

    @field:SerializedName("followers")
    var followers: Int? = null,

    @field:SerializedName("following")
    var following: Int? = null
)