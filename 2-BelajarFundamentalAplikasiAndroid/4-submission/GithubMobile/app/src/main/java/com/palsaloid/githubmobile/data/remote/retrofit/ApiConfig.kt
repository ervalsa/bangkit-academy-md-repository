package com.palsaloid.githubmobile.data.remote.retrofit

import com.palsaloid.githubmobile.BuildConfig.BASE_URL
import com.palsaloid.githubmobile.BuildConfig.DEBUG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                if (DEBUG) {
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                } else {
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.NONE
                    )
                }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit2 = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit2.create(ApiService::class.java)
        }
    }
}