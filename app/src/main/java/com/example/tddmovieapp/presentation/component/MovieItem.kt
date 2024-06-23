package com.example.tddmovieapp.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MovieItem(
    onItemClick: () -> Unit,
    imageUrl: String,
    title: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp).padding(20.dp).border(
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageFromUrl(
            modifier = Modifier.height(200.dp),
            url = { imageUrl }
        )
        Text(text = title)
    }
}


@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(
        onItemClick = {},
        "https://img.freepik.com/free-photo/view-3d-cinema-elements_23-2150720822.jpg",
        "Iron Man"
    )
}