package com.palsaloid.githubmobile.data.remote.retrofit

import com.palsaloid.githubmobile.BuildConfig.API_KEY
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {

    @Headers("Authorization: token $API_KEY")
    @GET("/users")
    fun getAllUsers() : Call<UserResponse>

    @Headers("Authorization: token $API_KEY")
    @GET("/users?q={username}")
    fun searchUser(
        @Path("username") username: String
    ) : Call<UserResponse>

    @Headers("Authorization: token $API_KEY")
    @GET("/users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<UserResponse>

    @Headers("Authorization: token $API_KEY")
    @GET("/users/{username}/followers")
    fun getListFollower(
        @Path("username") username: String
    ) : Call<UserResponse>

    @Headers("Authorization: token $API_KEY")
    @GET("/users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String
    ) : Call<UserResponse>
}