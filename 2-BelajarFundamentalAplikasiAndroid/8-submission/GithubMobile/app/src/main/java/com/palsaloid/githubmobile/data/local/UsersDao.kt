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
    suspend fun insertUser(users: UsersEntity)

    @Update
    suspend fun updateUsers(users: UsersEntity)

    @Delete
    suspend fun deleteUsers(users: UsersEntity)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE name = :name AND favorited = 1)")
    suspend fun isUsersFavorited(name: String): Boolean
}