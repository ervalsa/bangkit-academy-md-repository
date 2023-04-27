package com.palsaloid.restaurantreview.retrofit

import com.google.gson.annotations.SerializedName

data class RestaurantResponse(

    @field:SerializedName("restaurant")
    val restaurant: Restaurant,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)