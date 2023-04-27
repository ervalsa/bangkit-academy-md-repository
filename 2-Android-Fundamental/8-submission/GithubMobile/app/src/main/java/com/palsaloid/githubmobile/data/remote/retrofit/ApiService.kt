package com.palsaloid.githubmobile.data.remote.retrofit

import com.palsaloid.githubmobile.data.remote.response.UserListResponse
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getAllUsers() : UserListResponse

    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ) : Call<UserListResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<UserResponse>

    @GET("users/{username}/followers")
    fun getListFollower(
        @Path("username") username: String
    ) : Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String
    ) : Call<List<UserResponse>>
}