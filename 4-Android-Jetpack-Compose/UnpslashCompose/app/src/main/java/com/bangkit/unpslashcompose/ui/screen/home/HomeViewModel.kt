package com.bangkit.unpslashcompose.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.unpslashcompose.data.PhotoRepository
import com.bangkit.unpslashcompose.model.Photo
import com.bangkit.unpslashcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PhotoRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Photo>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Photo>>>
        get() = _uiState

    fun getAllPhotos() {
        viewModelScope.launch {
            repository.getAllPhotos()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { photos ->
                    _uiState.value = UiState.Success(photos)
                }
        }
    }
}