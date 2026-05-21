package com.example.socialconnect.Presentation.ProfileScreen.Component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridOn
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileTabSection(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,

) {

    TabRow(
        selectedTabIndex = selectedTab,
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background
    ) {

        // 🔵 POSTS TAB
        Tab(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },



            icon = {
                Icon(
                    imageVector = Icons.Outlined.GridOn,
                    contentDescription = "Posts",

                )
            }
        )

        // 🔵 VIDEOS TAB
        Tab(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },



            icon = {
                Icon(
                    imageVector = Icons.Outlined.SmartDisplay,
                    contentDescription = "Videos"
                )
            }
        )
    }
}
@Preview
@Composable
fun ShowTab(){
    ProfileTabSection(selectedTab = 0, onTabSelected = {},)
}