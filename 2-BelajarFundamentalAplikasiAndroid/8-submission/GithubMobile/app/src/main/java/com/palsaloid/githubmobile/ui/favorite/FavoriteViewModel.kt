package com.palsaloid.githubmobile.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palsaloid.githubmobile.data.UsersRepository
import com.palsaloid.githubmobile.data.entity.UsersEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    fun getFavoritedUsers() = usersRepository.getFavoriteUser()

    fun isFavoriteUser(name: String) = usersRepository.isFavoriteUser(name)

    fun deleteUsers(user: UsersEntity) {
        viewModelScope.launch {
            usersRepository.deleteUser(user)
        }
    }
}