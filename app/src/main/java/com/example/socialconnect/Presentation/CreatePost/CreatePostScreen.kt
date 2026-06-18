package com.example.socialconnect.Presentation.CreatePost

import CustomAppBar
import android.content.Context
import android.net.Uri
import android.util.Log
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
import androidx.compose.runtime.key
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
import androidx.media3.common.util.UnstableApi
import java.io.File

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    navController: NavController,
    viewModel: CreatePostViewModel = hiltViewModel(),
    mediaUri: String,
    mediaType: String,
    postId: String? = null,

) {

    val state = viewModel.state.collectAsState().value
    Log.d("VIDEO_URI", state.selectedMediaUri)
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val fixedUri = remember(state.selectedMediaUri) {
        getRealPathUri(context, Uri.parse(state.selectedMediaUri))
    }


    LaunchedEffect(postId, mediaUri, mediaType) {
        android.util.Log.d(
            "CREATE_POST",
            "postId=$postId, mediaUri=$mediaUri, mediaType=$mediaType"
        )


        if (!postId.isNullOrBlank()) {
            viewModel.loadPost(postId)
        } else if (mediaUri.isNotBlank()) {
            viewModel.setSelectedMedia(
                Uri.parse(mediaUri),
                mediaType
            )
        }
    }

    LaunchedEffect(state.isPosted) {
        if (state.isPosted) navController.popBackStack()
    }
    Log.d("CREATE_POST", "postId = $postId")
    Log.d("CREATE_POST", "mediaUri = $mediaUri")
    Log.d("CREATE_POST", "mediaType = $mediaType")


        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {

            CustomAppBar(
                title = if(state.isEditMode)
                    stringResource(R.string.UpdatePost_text)
                else
                    stringResource(R.string.CreatePost_text),
                onBackClick = {
                    navController.popBackStack()
                }
            )

            if (state.selectedMediaUri.isNotBlank() && state.mediaType == "image") {

                key(state.selectedMediaUri) {
                    AsyncImage(
                        model = state.selectedMediaUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }

            } else if (state.selectedMediaUri.isNotBlank() && state.mediaType == "video") {
                val uri = remember(state.selectedMediaUri) {
                    Uri.parse(state.selectedMediaUri)
                }

                val exoPlayer = remember(uri) {
                    ExoPlayer.Builder(context).build().apply {
                        setMediaItem(MediaItem.fromUri(uri))
                        prepare()
                        playWhenReady = true
                    }
                }

                DisposableEffect(exoPlayer) {
                    onDispose { exoPlayer.release() }
                }

                AndroidView(
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = exoPlayer
                            useController = true
                            resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
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
                    text = if(state.isEditMode)
                       stringResource(R.string.Update_text)
                    else
                        stringResource(R.string.Post_text),
                    onClick = {
                        if(state.isEditMode){
                            viewModel.updatePost()
                        }else{
                            viewModel.createPost(context)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
fun getRealPathUri(context: Context, uri: Uri): Uri {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_video.mp4")

        file.outputStream().use { output ->
            inputStream?.copyTo(output)
        }

        Uri.fromFile(file)
    } catch (e: Exception) {
        uri
    }
}
