package com.palsaloid.dicodingstoryapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.palsaloid.dicodingstoryapp.data.StoryRepository
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class HomeViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun getStories(token: String) = storyRepository.getStories(token).cachedIn(viewModelScope)

}