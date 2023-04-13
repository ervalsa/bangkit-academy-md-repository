package com.palsaloid.storydicoding.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.palsaloid.storydicoding.data.local.datastore.UserPreference
import com.palsaloid.storydicoding.data.model.UserModel
import kotlinx.coroutines.launch

class HomeViewModel(private val preferences: UserPreference) : ViewModel() {

    fun getUser(): LiveData<UserModel> =
        preferences.getUser().asLiveData()

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            preferences.saveUser(user)
        }
    }
}