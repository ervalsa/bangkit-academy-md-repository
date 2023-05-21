package com.bangkit.unpslashcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.unpslashcompose.R
import com.bangkit.unpslashcompose.ui.theme.UnsplashComposeTheme

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    imageUrl: Int,
    name: String
) {
    Card(
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colors.primary)
        ) {
            Image(
                painter = painterResource(id = imageUrl),
                contentDescription = "Item Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoItemPreview() {
    UnsplashComposeTheme {
        PhotoItem(
            imageUrl = R.drawable.catalogue_6,
            name = "Horizen T-shirt Jujutsu Kaisen Inumaki Toge"
        )
    }
}