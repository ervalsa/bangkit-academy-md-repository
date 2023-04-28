package com.palsaloid.dicodingstoryapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryItem

@Dao
interface StoryDao {

    @Query("SELECT * FROM story")
    fun getStories() : LiveData<List<StoryItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStory(news: List<StoryItem>)

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}