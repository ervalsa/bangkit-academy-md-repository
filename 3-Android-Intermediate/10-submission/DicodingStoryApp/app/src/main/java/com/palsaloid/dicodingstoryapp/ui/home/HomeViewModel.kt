package com.palsaloid.dicodingstoryapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.palsaloid.dicodingstoryapp.data.StoryRepository


class HomeViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun getStories(token: String) = storyRepository.getStories(token).cachedIn(viewModelScope)

}