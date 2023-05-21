package com.bangkit.unpslashcompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.unpslashcompose.data.PhotoRepository
import com.bangkit.unpslashcompose.ui.screen.detail.DetailViewModel
import com.bangkit.unpslashcompose.ui.screen.home.HomeViewModel

class ViewModelFactory(
    private val repository: PhotoRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }

        throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}