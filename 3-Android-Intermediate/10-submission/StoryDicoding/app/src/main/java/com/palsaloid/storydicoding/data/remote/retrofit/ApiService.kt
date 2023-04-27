package com.palsaloid.storydicoding.data.remote.retrofit

import com.palsaloid.storydicoding.data.remote.response.auth.LoginResponse
import com.palsaloid.storydicoding.data.remote.response.auth.RegisterResponse
import com.palsaloid.storydicoding.data.remote.response.story.DetailStoryResponse
import com.palsaloid.storydicoding.data.remote.response.story.FileUploadResponse
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

    @GET("stories?location=1")
    fun getStoryWithLocation(
        @Header("Authorization") token: String,
        @Query("size") size: Int = 200
    ) : Call<ListStoryResponse>

    @GET("stories/{id}")
    fun getDetailStory(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Call<DetailStoryResponse>

    @Multipart
    @POST("stories")
    fun addStory(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : Call<FileUploadResponse>
}