package com.example.socialconnect.Presentation.CreatePost

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
        topBar = {
            TopAppBar(title = { Text("Create Post") })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
                .imePadding()
                .navigationBarsPadding()
        ) {

            // IMAGE PREVIEW
            if (state.mediaType == "image") {

                AsyncImage(
                    model = state.selectedMediaUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
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
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // CAPTION
            OutlinedTextField(
                value = state.caption,
                onValueChange = viewModel::onCaptionChange,
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                placeholder = { Text("Write a caption...") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // BUTTON
            AppButton(
                text = "Post",
                onClick = { viewModel.createPost(context) },
                enabled = !state.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}