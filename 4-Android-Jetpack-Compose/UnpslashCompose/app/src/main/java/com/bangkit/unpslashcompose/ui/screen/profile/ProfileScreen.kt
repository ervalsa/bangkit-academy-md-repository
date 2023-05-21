package com.bangkit.unpslashcompose.ui.screen.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.unpslashcompose.R
import com.bangkit.unpslashcompose.UnsplashApp
import com.bangkit.unpslashcompose.ui.theme.UnsplashComposeTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    ProfileContent(
        image = R.drawable.my_photo
    )
}

@Composable
fun ProfileContent(
    @DrawableRes image: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Developer Photo",
            modifier = Modifier
                .size(width = 100.dp, height = 100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Ervalsa Dwi Nanda")
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "ervalsananda@gmail.com")
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    UnsplashComposeTheme {
        ProfileContent(
            image = R.drawable.my_photo
        )
    }
}