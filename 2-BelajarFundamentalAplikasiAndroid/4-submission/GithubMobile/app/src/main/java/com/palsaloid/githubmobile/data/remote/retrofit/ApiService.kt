package com.palsaloid.githubmobile.data.remote.retrofit

import com.palsaloid.githubmobile.BuildConfig.API_KEY
import com.palsaloid.githubmobile.data.remote.response.UserListResponse
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: token $API_KEY")
    @GET("users")
    fun getAllUsers() : Call<List<UserResponse>>

    @Headers("Authorization: token $API_KEY")
    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ) : Call<UserListResponse>

    @Headers("Authorization: token $API_KEY")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<UserResponse>

    @Headers("Authorization: token $API_KEY")
    @GET("users/{username}/followers")
    fun getListFollower(
        @Path("username") username: String
    ) : Call<List<UserResponse>>

    @Headers("Authorization: token $API_KEY")
    @GET("users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String
    ) : Call<List<UserResponse>>
}