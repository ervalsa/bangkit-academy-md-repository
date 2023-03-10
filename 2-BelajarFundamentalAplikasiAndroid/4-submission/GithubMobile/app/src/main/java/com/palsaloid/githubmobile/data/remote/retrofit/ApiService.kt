package com.palsaloid.githubmobile.data.remote.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/users")
    fun getAllUsers(

    )
}