package com.palsaloid.storydicoding.data.local

import androidx.lifecycle.LiveData
import com.palsaloid.storydicoding.data.local.entity.StoryEntity
import com.palsaloid.storydicoding.data.local.room.StoryDao

class LocalDataSource private constructor(private val storyDao: StoryDao) {

    fun getAllStory(): LiveData<List<StoryEntity>> = storyDao.getAllStory()

    fun insertStory(storyList: List<StoryEntity>) = storyDao.insertStory(storyList)

    fun deleteStory() = storyDao.deleteAll()

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(storyDao: StoryDao): LocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(storyDao)
            }
    }
}