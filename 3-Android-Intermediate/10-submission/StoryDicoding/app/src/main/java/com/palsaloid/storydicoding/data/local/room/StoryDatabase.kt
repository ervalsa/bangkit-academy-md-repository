package com.palsaloid.storydicoding.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.palsaloid.storydicoding.data.local.entity.RemoteKeysEntity
import com.palsaloid.storydicoding.data.local.entity.StoryEntity

@Database(
    entities = [StoryEntity::class, RemoteKeysEntity::class],
    version = 3,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        fun getInstance(context: Context): StoryDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java, "Story.db"
                ).build()
            }
    }
}