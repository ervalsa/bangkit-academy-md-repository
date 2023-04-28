package com.palsaloid.dicodingstoryapp.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class LoginResult (
    @field:SerializedName("userid")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)