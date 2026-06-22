package com.example.socialconnect.Component


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FloatingBottomBar(
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    profileImage: String,
    onItemSelected: (Int) -> Unit,
    onProfileClick: () -> Unit
) {

    Row(
        modifier = modifier
    ) {

        BottomBar(
            selectedIndex = selectedIndex,
            onItemSelected = onItemSelected,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))
            ProfileFloatingButton(
                image = profileImage,
                onClick = onProfileClick
            )

    }
}