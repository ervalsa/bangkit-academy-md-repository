package com.palsaloid.githubmobile.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersEntity(
    @field:PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "name")
    var name: String,

    @field:ColumnInfo(name = "login")
    var login: String,

    @field:ColumnInfo(name = "avatar_url")
    var avatarUrl: String,

    @field:ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean
)