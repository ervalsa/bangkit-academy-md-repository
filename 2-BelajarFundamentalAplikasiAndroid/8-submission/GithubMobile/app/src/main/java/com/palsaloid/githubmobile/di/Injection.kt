package com.palsaloid.githubmobile.di

import android.content.Context
import com.palsaloid.githubmobile.data.UsersRepository
import com.palsaloid.githubmobile.data.local.UsersDatabase

object Injection {

    fun provideRepository(context: Context): UsersRepository {
        val database = UsersDatabase.getInstance(context)
        val dao = database.usersDao()

        return UsersRepository.getInstance(dao)
    }
}