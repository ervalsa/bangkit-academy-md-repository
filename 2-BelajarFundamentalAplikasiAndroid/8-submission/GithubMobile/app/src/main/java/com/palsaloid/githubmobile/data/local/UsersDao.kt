package com.palsaloid.githubmobile.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.palsaloid.githubmobile.data.entity.UsersEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<UsersEntity>>

    @Query("SELECT * FROM users where favorited = 1")
    fun getFavoritedUsers(): LiveData<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(users: List<UsersEntity>)

    @Update
    suspend fun updateNews(users: UsersEntity)

    @Query("DELETE FROM users WHERE favorited = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE name = :name AND favorited = 1)")
    suspend fun isUsersFavorited(name: String): Boolean
}