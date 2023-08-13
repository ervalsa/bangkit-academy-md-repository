package com.palsa.myreactivesearch.network

import com.palsa.myreactivesearch.model.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("mapbox.places/{query}.json")
    suspend fun getCountry(
        @Query("query") query: String,
        @Query("access_token") accessToken: String,
        @Query("autocomplete") autoComplete: Boolean = true
    ) : PlaceResponse
}