package com.bangkit.unpslashcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.unpslashcompose.data.PhotoRepository
import com.bangkit.unpslashcompose.model.Photo
import com.bangkit.unpslashcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: PhotoRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Photo>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Photo>>
        get() = _uiState

    fun getPhotoById(photoId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getPhotoById(photoId))
        }
    }
}