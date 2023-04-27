package com.palsaloid.githubmobile.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.palsaloid.githubmobile.ui.setting.SettingPreference
import com.palsaloid.githubmobile.ui.setting.SettingViewModel

class SettingViewModelFactory(private val pref: SettingPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}