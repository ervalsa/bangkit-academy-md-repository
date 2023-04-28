package com.palsaloid.dicodingstoryapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.palsaloid.dicodingstoryapp.data.StoryRepository
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeViewModel(storyRepository: StoryRepository, private val userPreference: UserPreference) : ViewModel() {

//    private var token: String
//
//    init {
//        runBlocking {
//            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTNoSkY1TzFYdXJiX3JwMmwiLCJpYXQiOjE2ODI2ODYzMjN9.KyDTuzA2xWshc1sRVrk6PmpCylnkXcbKAzNi_CTIIS8"
//        }
//    }

    val getStories: LiveData<PagingData<StoryItem>> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
//            val token = userPreference.getUser().first().token
            val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTNoSkY1TzFYdXJiX3JwMmwiLCJpYXQiOjE2ODI2ODYzMjN9.KyDTuzA2xWshc1sRVrk6PmpCylnkXcbKAzNi_CTIIS8"
            emitSource(storyRepository.getStories(token).cachedIn(viewModelScope))
        }

}