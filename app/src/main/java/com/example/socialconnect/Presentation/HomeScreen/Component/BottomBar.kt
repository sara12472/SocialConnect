package com.example.socialconnect.Presentation.HomeScreen.Component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .wrapContentWidth()
            .navigationBarsPadding(),

        shape = RoundedCornerShape(30.dp),

        tonalElevation = 8.dp,

        color = MaterialTheme.colorScheme.surface

        ) {

        NavigationBar(
            modifier = Modifier.height(60.dp),
           containerColor = Color.Transparent
           ) {


            NavigationBarItem(

                selected = selectedIndex == 0,
                onClick = { onItemSelected(0) },

                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Home"
                    )
                }
            )
            NavigationBarItem(

                selected = selectedIndex == 1,
                onClick = { onItemSelected(1) },

                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Message,
                        contentDescription = "Home"
                    )
                }
            )
            NavigationBarItem(

                selected = selectedIndex == 2,
                onClick = { onItemSelected(2) },

                icon = {
                    Icon(
                        imageVector = Icons.Outlined.SmartDisplay,
                        contentDescription = "Home"
                    )
                }
            )
            NavigationBarItem(
                selected = selectedIndex == 3,
                onClick = { onItemSelected(3) },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search"
                    )
                }
            )


            NavigationBarItem(
                selected = selectedIndex == 4,
                onClick = { onItemSelected(4) },

                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings"
                    )
                }
            )


        }
    }

}

@Preview
@Composable
fun ShowBottomBar(){
    BottomBar(onItemSelected = {}, selectedIndex = 1)
}