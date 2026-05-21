package com.example.socialconnect.Presentation.EditProfileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialconnect.Component.AppTextField

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(), ) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            // 🔵 TOP BAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 🔵 PROFILE IMAGE
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    Image(
                        painter = painterResource(id = state.profileImage),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "Edit Picture",
                        style=MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(25.dp))

                    // 🔵 NAME
                    AppTextField(
                        value = state.name,
                        onValueChange = viewModel::onNameChange,
                        hint = "Name",
                        modifier = Modifier.fillMaxWidth()

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 🔵 USERNAME
                    AppTextField(
                        value = state.username,
                        onValueChange = viewModel::onUsernameChange,
                        hint = "Username",
                        modifier = Modifier.fillMaxWidth()

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 🔵 BIO
                    AppTextField(
                        value = state.bio,
                        onValueChange = viewModel::onBioChange,
                        hint = "Bio",
                        modifier = Modifier.fillMaxWidth()

                    )

                }



            }

        }
    }
}

@Preview
@Composable
fun ShowEditScreen(){
   val viewModel: EditProfileViewModel = hiltViewModel()

    EditProfileScreen(viewModel)
}