package com.palsaloid.storydicoding.ui.login

import androidx.lifecycle.ViewModel
import com.palsaloid.storydicoding.data.local.datastore.UserPreference
import com.palsaloid.storydicoding.data.remote.retrofit.ApiConfig

class LoginViewModel(private val preferences: UserPreference) : ViewModel() {

    fun login(email: String, password: String) {
        val client = ApiConfig.getApiService().login(email, password)
    }
}