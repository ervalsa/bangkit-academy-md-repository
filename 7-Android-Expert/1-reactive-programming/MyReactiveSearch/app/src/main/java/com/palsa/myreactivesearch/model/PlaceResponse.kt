package com.palsa.myreactivesearch.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @field:SerializedName("features")
    val features: List<PlacesItem>
)
