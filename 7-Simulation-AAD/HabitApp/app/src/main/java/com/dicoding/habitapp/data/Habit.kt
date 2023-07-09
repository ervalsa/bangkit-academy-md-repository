package com.dicoding.habitapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//TODO 1 : Define a local database table using the schema in app/schema/habits.json
@Parcelize
@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "minutesFocus")
    val minutesFocus: Long,

    @ColumnInfo(name = "startTime")
    val startTime: String,

    @ColumnInfo(name = "priorityLevel")
    val priorityLevel: String
): Parcelable
