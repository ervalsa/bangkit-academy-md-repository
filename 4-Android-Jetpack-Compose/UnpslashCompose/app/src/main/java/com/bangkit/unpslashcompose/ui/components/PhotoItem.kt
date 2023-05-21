package com.bangkit.unpslashcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.unpslashcompose.R
import com.bangkit.unpslashcompose.ui.theme.UnsplashComposeTheme

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    imageUrl: Int,
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = imageUrl),
            contentDescription = "Item Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoItemPreview() {
    UnsplashComposeTheme {
        PhotoItem(
            imageUrl = R.drawable.dummy_photo
        )
    }
}