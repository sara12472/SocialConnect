package com.example.socialconnect.Presentation.CreatePost

import CustomAppBar
import android.net.Uri
import com.example.socialconnect.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialconnect.Component.AppButton
import androidx.media3.common.MediaItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    navController: NavController,
    viewModel: CreatePostViewModel = hiltViewModel(),
    mediaUri: String,
    mediaType: String,
) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(mediaUri, mediaType) {
        if (state.selectedMediaUri.isEmpty()) {
            viewModel.setSelectedMedia(Uri.parse(mediaUri), mediaType)
        }
    }

    LaunchedEffect(state.isPosted) {
        if (state.isPosted) navController.popBackStack()
    }
    Scaffold(
        containerColor = Color.Transparent
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .navigationBarsPadding()
        ) {

            CustomAppBar(
                title = stringResource(R.string.CreatePost_text),
                onBackClick = {
                    navController.popBackStack()
                }
            )

            if (state.mediaType == "image") {

                AsyncImage(
                    model = state.selectedMediaUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

            } else if (state.mediaType == "video") {

                val player = remember(state.selectedMediaUri) {
                    ExoPlayer.Builder(context).build().apply {
                        setMediaItem(
                            MediaItem.fromUri(
                                Uri.parse(state.selectedMediaUri)
                            )
                        )
                        prepare()
                    }
                }

                DisposableEffect(player) {
                    onDispose {
                        player.release()
                    }
                }

                AndroidView(
                    factory = {
                        PlayerView(it).apply {
                            this.player = player
                            useController = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.caption,
                onValueChange = viewModel::onCaptionChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                minLines = 4,
                placeholder = {
                    Text("Write a caption...")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            if (state.isLoading) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            } else {

                AppButton(
                    text = stringResource(R.string.Post_text),
                    onClick = {
                        viewModel.createPost(context)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}