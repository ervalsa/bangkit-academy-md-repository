package com.bangkit.unpslashcompose.di

import com.bangkit.unpslashcompose.data.PhotoRepository

object Injection {

    fun providerRepository(): PhotoRepository {
        return PhotoRepository.getInstance()
    }
}