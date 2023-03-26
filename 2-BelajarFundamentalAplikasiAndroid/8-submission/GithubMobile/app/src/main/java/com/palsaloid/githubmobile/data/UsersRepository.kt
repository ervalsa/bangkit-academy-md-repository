package com.palsaloid.githubmobile.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.palsaloid.githubmobile.data.entity.UsersEntity
import com.palsaloid.githubmobile.data.local.UsersDao
import com.palsaloid.githubmobile.data.remote.retrofit.ApiService

class UsersRepository private constructor(
    private val usersDao: UsersDao
) {

    fun getFavoriteUser(): LiveData<List<UsersEntity>> = usersDao.getFavoriteUser()

    suspend fun insertUser(user: UsersEntity) {
        usersDao.insertUser(user)
    }

    suspend fun deleteUser(user: UsersEntity) {
        usersDao.deleteUser(user)
    }

    fun isFavoriteUser(name: String): LiveData<UsersEntity> = usersDao.isFavoriteUser(name)

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