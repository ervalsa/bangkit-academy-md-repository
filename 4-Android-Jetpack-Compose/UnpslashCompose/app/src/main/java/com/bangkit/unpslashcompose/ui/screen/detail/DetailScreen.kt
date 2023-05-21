package com.bangkit.unpslashcompose.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.unpslashcompose.R
import com.bangkit.unpslashcompose.di.Injection
import com.bangkit.unpslashcompose.ui.ViewModelFactory
import com.bangkit.unpslashcompose.ui.common.UiState
import com.bangkit.unpslashcompose.ui.theme.UnsplashComposeTheme

@Composable
fun DetailScreen(
    photoId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.providerRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getPhotoById(photoId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.imageUrl,
                    onBackClick = navigateBack
                )
            }

            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
            ) {
               Icon(
                   imageVector = Icons.Default.ArrowBack,
                   contentDescription = stringResource(R.string.navigation_back),
                   modifier = Modifier
                       .padding(16.dp)
                       .clickable {
                           onBackClick()
                       }
               )
            }
            Image(
                painter = painterResource(id = image),
                contentDescription = "Item Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    UnsplashComposeTheme {
        DetailContent(
            image = R.drawable.dummy_photo,
            onBackClick = { /*TODO*/ })
    }
}