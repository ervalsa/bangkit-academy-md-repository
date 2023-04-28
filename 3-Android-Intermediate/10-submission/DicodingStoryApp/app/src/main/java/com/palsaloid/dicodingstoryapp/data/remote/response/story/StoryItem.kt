package com.palsaloid.dicodingstoryapp.data.remote.response.story

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "story")
data class StoryItem(
    @field:PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: String,

    @field:ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String,

    @field:ColumnInfo(name = "description")
    @field:SerializedName("description")
    val description: String,

    @field:ColumnInfo(name = "photoUrl")
    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:ColumnInfo(name = "createdAt")
    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:ColumnInfo(name = "lat")
    @field:SerializedName("lat")
    val lat: Float,

    @field:ColumnInfo(name = "lon")
    @field:SerializedName("lon")
    val lon: Float
)