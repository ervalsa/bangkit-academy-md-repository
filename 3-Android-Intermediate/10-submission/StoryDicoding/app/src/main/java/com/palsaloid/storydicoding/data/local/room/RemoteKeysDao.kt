package com.palsaloid.storydicoding.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.palsaloid.storydicoding.data.local.entity.RemoteKeysEntity

@Dao
interface  RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeysEntity: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()
}