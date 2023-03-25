package com.palsaloid.githubmobile.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palsaloid.githubmobile.data.UsersRepository
import com.palsaloid.githubmobile.data.entity.UsersEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    fun getFavoritedUsers() = usersRepository.getFavoritedUsers()

    fun saveUsers(users: UsersEntity) {
        viewModelScope.launch {
            usersRepository.setUsersFavorite(users, true)
        }
    }

    fun deleteUsers(users: UsersEntity) {
        viewModelScope.launch {
            usersRepository.setUsersFavorite(users, false)
        }
    }
}