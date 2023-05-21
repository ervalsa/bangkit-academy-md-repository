package com.bangkit.unpslashcompose.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.unpslashcompose.di.Injection
import com.bangkit.unpslashcompose.model.Photo
import com.bangkit.unpslashcompose.ui.ViewModelFactory
import com.bangkit.unpslashcompose.ui.common.UiState
import com.bangkit.unpslashcompose.ui.components.PhotoItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.providerRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(
        initial = UiState.Loading
    ).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllPhotos()
            }

            is UiState.Success -> {
                HomePhotoContent(
                    photos = uiState.data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomePhotoContent(
    photos: List<Photo>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(photos) { data ->
            PhotoItem(
                imageUrl = data.imageUrl,
                name = data.name,
                modifier = Modifier.clickable {
                    navigateToDetail(data.id)
                }
            )
        }
    }
}