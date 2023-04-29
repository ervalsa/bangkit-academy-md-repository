package com.palsaloid.dicodingstoryapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.data.model.UserModel
import kotlinx.coroutines.launch

class UserViewModel(private val preferences: UserPreference) : ViewModel() {

    fun getUser(): LiveData<UserModel> =
        preferences.getUser().asLiveData()

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            preferences.saveUser(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            preferences.logout()
        }
    }
}

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(
    private val preference: UserPreference
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(preference) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}