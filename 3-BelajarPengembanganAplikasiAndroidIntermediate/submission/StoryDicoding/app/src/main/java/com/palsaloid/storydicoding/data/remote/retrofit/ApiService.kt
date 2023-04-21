package com.palsaloid.storydicoding.data.remote.retrofit

import com.palsaloid.storydicoding.data.remote.response.auth.LoginResponse
import com.palsaloid.storydicoding.data.remote.response.auth.RegisterResponse
import com.palsaloid.storydicoding.data.remote.response.story.FileUploadResponse
import com.palsaloid.storydicoding.data.remote.response.story.StoryResponse
import com.palsaloid.storydicoding.data.remote.response.story.ListStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<RegisterResponse>

    @GET("stories")
    fun getAllStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("location") location: Int? = 0
    ) : Call<ListStoryResponse>

    @Multipart
    @POST("stories")
    fun addStory(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : Call<FileUploadResponse>

    @GET("stories/{id}")
    fun getDetailStory(
        @Path("id") id: String
    ) : Call<StoryResponse>
}