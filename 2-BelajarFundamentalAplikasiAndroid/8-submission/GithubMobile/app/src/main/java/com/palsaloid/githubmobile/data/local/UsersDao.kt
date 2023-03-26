package com.palsaloid.githubmobile.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.palsaloid.githubmobile.data.UsersRepository
import com.palsaloid.githubmobile.data.entity.UsersEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM users where is_favorite = 1")
    fun getFavoriteUser(): LiveData<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UsersEntity)

    @Delete
    suspend fun deleteUser(use: UsersEntity)
}