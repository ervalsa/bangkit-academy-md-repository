package com.palsaloid.dicodingstoryapp.data.remote.retrofit

import com.palsaloid.dicodingstoryapp.data.remote.response.auth.LoginResponse
import com.palsaloid.dicodingstoryapp.data.remote.response.auth.RegisterResponse
import com.palsaloid.dicodingstoryapp.data.remote.response.story.DetailStoryResponse
import com.palsaloid.dicodingstoryapp.data.remote.response.story.FileUploadResponse
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : StoryResponse

    @GET("stories?location=1")
    fun getStoryLocation(
        @Header("Authorization") token: String,
        @Query("size") size: Int = 100
    ) : Call<StoryResponse>

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
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float?,
        @Part("lon") lon: Float?
    ) : Call<FileUploadResponse>
}