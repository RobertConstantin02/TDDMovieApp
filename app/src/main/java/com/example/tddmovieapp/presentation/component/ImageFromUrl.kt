package com.example.tddmovieapp.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ImageFromUrl(
    url: () -> String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(url())
            .size(Size.ORIGINAL)
            .crossfade(true).build(),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}