package com.example.socialconnect.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.socialconnect.R

@Composable
fun ProfileFloatingButton(
    image: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Card(
        shape = CircleShape,
        modifier = modifier.size(55.dp)

            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        if (image.isNullOrEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Default Avatar",
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = image,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}