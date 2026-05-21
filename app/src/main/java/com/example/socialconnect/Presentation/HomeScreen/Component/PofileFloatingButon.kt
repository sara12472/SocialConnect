package com.example.socialconnect.Presentation.HomeScreen.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ProfileFloatingButton(
    image: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Card(
        shape = CircleShape,
        modifier = modifier.size(55.dp)

            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Image(
            painter = painterResource(id = image),
            contentDescription = "Profile",
           modifier = Modifier.fillMaxSize()
        )
    }
}