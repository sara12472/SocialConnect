package com.example.socialconnect.Presentation.HomeScreen.Component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeTopBar(
    onAddClick: () -> Unit,
    onHeartClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()

            .padding(horizontal = 16.dp, vertical = 12.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row {

            Text(
                text = "Social",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Connect",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Row() {

            IconButton(onClick = onAddClick) {

                Icon(
                    imageVector = Icons.Outlined.AddBox,
                    contentDescription = "Add"
                )
            }

            IconButton(onClick = onHeartClick) {

                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Heart"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowHomeTopBar(){

    HomeTopBar(onAddClick = {}, onHeartClick = {})

}