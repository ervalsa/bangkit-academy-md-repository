package com.palsaloid.githubmobile.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersEntity(
    @field:ColumnInfo(name = "name")
    @field:PrimaryKey(autoGenerate = false)
    val name: String,

    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @field:ColumnInfo(name = "favorited")
    var isFavorite: Boolean
)