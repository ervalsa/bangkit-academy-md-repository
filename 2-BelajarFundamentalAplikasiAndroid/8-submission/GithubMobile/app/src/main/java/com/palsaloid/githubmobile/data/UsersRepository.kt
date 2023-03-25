package com.palsaloid.githubmobile.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.palsaloid.githubmobile.data.entity.UsersEntity
import com.palsaloid.githubmobile.data.local.UsersDao
import com.palsaloid.githubmobile.data.local.UsersDatabase
import com.palsaloid.githubmobile.data.remote.retrofit.ApiService
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UsersRepository private constructor(
    private val usersDao: UsersDao,
) {
    fun getFavoritedUsers(): LiveData<List<UsersEntity>> {
        return usersDao.getFavoritedUsers()
    }

    suspend fun insert(userEntity: UsersEntity) {
        usersDao.insertUsers(userEntity)
    }

    suspend fun isFavorited(name: String): Boolean {
        usersDao.isUsersFavorited(name)
        return true
    }

    suspend fun setUsersFavorite(users: UsersEntity, favoriteState: Boolean) {
        users.isFavorite = favoriteState
        usersDao.updateNews(users)
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