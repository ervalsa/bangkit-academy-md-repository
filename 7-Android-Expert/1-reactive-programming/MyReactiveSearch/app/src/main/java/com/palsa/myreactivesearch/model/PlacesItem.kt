package com.palsa.myreactivesearch.model

import com.google.gson.annotations.SerializedName

data class PlacesItem(
    @field:SerializedName("place_name")
    val placeName: String
)
