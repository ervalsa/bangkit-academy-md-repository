package com.palsaloid.githubmobile.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.palsaloid.githubmobile.data.entity.UsersEntity
import com.palsaloid.githubmobile.data.local.UsersDao
import com.palsaloid.githubmobile.data.remote.retrofit.ApiService

class UsersRepository private constructor(
    private val apiService: ApiService,
    private val usersDao: UsersDao
) {

    fun getFavoritedUsers(): LiveData<List<UsersEntity>> {
        return usersDao.getFavoritedUsers()
    }

    suspend fun setUsersFavorite(users: UsersEntity, favoriteState: Boolean) {
        users.isFavorite = favoriteState
        usersDao.updateNews(users)
    }

    companion object {
        @Volatile
        private var instance: UsersRepository? = null

        fun getInstance(
            apiService: ApiService,
            usersDao: UsersDao
        ): UsersRepository =
            instance ?: synchronized(this) {
                instance ?: UsersRepository(apiService, usersDao)
            }.also { instance = it }
    }
}