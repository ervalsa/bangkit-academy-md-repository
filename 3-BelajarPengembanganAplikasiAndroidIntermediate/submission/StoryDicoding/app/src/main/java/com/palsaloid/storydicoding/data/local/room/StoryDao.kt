package com.palsaloid.storydicoding.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.palsaloid.storydicoding.data.local.entity.StoryEntity

@Dao
interface StoryDao {

    @Query("SELECT * FROM story ")
    fun getAllStory(): LiveData<List<StoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStory(story: List<StoryEntity>)

    @Update
    fun updateStory(story: StoryEntity)

    @Query("DELETE FROM story")
    fun deleteAll()
}