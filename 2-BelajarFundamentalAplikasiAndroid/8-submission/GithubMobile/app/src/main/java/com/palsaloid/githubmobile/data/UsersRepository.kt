package com.palsaloid.githubmobile.data

import androidx.lifecycle.LiveData
import com.palsaloid.githubmobile.data.entity.UsersEntity
import com.palsaloid.githubmobile.data.local.UsersDao

class UsersRepository private constructor(
    private val usersDao: UsersDao,
) {
    fun getFavoritedUsers(): LiveData<List<UsersEntity>> {
        return usersDao.getFavoritedUsers()
    }

    suspend fun insert(userEntity: UsersEntity) {
        usersDao.insertUser(userEntity)
    }

    suspend fun delete(userEntity: UsersEntity) {
        usersDao.deleteUsers(userEntity)
    }

    suspend fun isFavorited(name: String): Boolean {
        usersDao.isUsersFavorited(name)
        return true
    }

    suspend fun setUsersFavorite(users: UsersEntity, favoriteState: Boolean) {
        users.isFavorite = favoriteState
        usersDao.updateUsers(users)
    }

    companion object {
        @Volatile
        private var instance: UsersRepository? = null

        fun getInstance(
            usersDao: UsersDao
        ): UsersRepository =
            instance ?: synchronized(this) {
                instance ?: UsersRepository(usersDao)
            }.also { instance = it }
    }
}