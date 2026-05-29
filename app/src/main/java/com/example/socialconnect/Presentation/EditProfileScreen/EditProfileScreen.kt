package com.example.socialconnect.Presentation.EditProfileScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.socialconnect.Component.AppTextField
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = hiltViewModel(),

    ) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val isSaved by viewModel.isSaved.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadUser()
    }
    LaunchedEffect(isSaved) {

        if (isSaved) {
            navController.popBackStack()
        }
    }



    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            uri?.let {

                viewModel.uploadImage(
                    it,
                    context
                )
            }
        }




    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            //  TOP BAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {
                    viewModel.saveProfile()

                }) {
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

            //  PROFILE IMAGE
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    AsyncImage(
                        model = if (state.profileImage.isNotEmpty())
                            state.profileImage
                        else
                            "https://i.pravatar.cc/300",

                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,


                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "Edit Picture",
                        style=MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            launcher.launch("image/*")
                        }
                    )
                    Spacer(modifier = Modifier.height(25.dp))

                    // NAME
                    AppTextField(
                        value = state.name,
                        onValueChange = viewModel::onNameChange,
                        hint = "Name",
                        modifier = Modifier.fillMaxWidth()

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // USERNAME
                    AppTextField(
                        value = state.username,
                        onValueChange = viewModel::onUsernameChange,
                        hint = "Username",
                        modifier = Modifier.fillMaxWidth()

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // BIO
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
    val navController= rememberNavController()
   val viewModel: EditProfileViewModel = hiltViewModel()

    EditProfileScreen(navController,viewModel)
}