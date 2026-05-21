package com.example.socialconnect.Presentation.ProfileScreen.Component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    userName: String,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit
) {

    CenterAlignedTopAppBar(

        title = {

            Text(
                text = userName,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        },

        navigationIcon = {

            IconButton(onClick = onBackClick) {

                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },

        actions = {

            IconButton(onClick = onEditClick) {

                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit"
                )
            }
        },

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),

        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    )
}

@Preview
@Composable
fun ShowTopBar(){
    ProfileTopBar(userName = "Sara", onBackClick = {}, onEditClick = {})

}