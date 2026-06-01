package com.example.socialconnect.Presentation.HomeScreen.Component


import android.media.browse.MediaBrowser
import android.os.Looper.prepare
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import com.example.socialconnect.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.toString
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.socialconnect.Data.Model.Post
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import com.example.socialconnect.Data.Model.Posts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.socialconnect.Navigation.Screen


@OptIn(UnstableApi::class)
@Composable
fun PostCard(
    post: Post,
    isVisible: Boolean,
    onUserClick: () -> Unit = {}
) {



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )

    ) {


        Column {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = post.userProfile,
                    contentDescription = post.userName,
                    modifier = Modifier
                        .clickable{
                            onUserClick
                        }
                        .size(45.dp)
                        .clip(CircleShape),
                            contentScale = ContentScale.Crop

                )

                Spacer(modifier = Modifier.size(10.dp))

                Column {

                    Text(
                        text = post.userName,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.clickable {
                            onUserClick()
                        }
                    )

                    Text(
                        text = getTimeAgo(post.timestamp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // 🔵 POST IMAGE
            if (post.mediaType == "image") {

                AsyncImage(
                    model = post.mediaUrl,
                    contentDescription = "Post Image",
                    modifier = Modifier.fillMaxWidth().height(400.dp),
                    contentScale = ContentScale.Crop
                )

            } else if (post.mediaType == "video") {
                val lifecycleOwner = LocalLifecycleOwner.current

                val context = LocalContext.current

                val exoPlayer = remember(post.mediaUrl) {
                    ExoPlayer.Builder(context).build().apply {

                        setMediaItem(
                           MediaItem.fromUri(post.mediaUrl)
                        )

                        prepare()

                        playWhenReady = false

                        repeatMode = ExoPlayer.REPEAT_MODE_ONE
                    }
                }
                LaunchedEffect(isVisible) {
                    if (isVisible) {
                        exoPlayer.play()
                    } else {
                        exoPlayer.pause()
                    }
                }

                DisposableEffect(lifecycleOwner,exoPlayer) {
                    val observer = LifecycleEventObserver { _, event ->

                        when (event) {
                            Lifecycle.Event.ON_PAUSE -> {
                                exoPlayer.pause()
                            }

                            Lifecycle.Event.ON_STOP -> {
                                exoPlayer.pause()
                            }

                            Lifecycle.Event.ON_DESTROY -> {
                                exoPlayer.release()
                            }

                            else -> Unit
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                        exoPlayer.release()
                    }
                }

                AndroidView(
                    factory = {
                        PlayerView(it).apply {
                            player = exoPlayer
                            useController = false
                            resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
            }


            // 🔵 ACTIONS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.clickable { }
                    )

                    Text("12")

                    Spacer(Modifier.width(10.dp))

                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = null,
                        modifier = Modifier.clickable { }
                    )

                    Text("13")

                    Spacer(Modifier.width(10.dp))

                    Icon(
                        imageVector = Icons.Outlined.Send,
                        contentDescription = null,
                        modifier = Modifier.clickable { }
                    )
                }

                IconButton(onClick = {}) {

                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = "Save"
                    )
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
            if (post.caption.isNotBlank()) {
                Text(
                    buildAnnotatedString {

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(post.userName)
                        }

                        append("  ")
                        append(post.caption)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 16.dp,
                        bottom = 15.dp
                    )
                )
            }
        }
    }
}
fun getTimeAgo(time: Long): String {
    val diff = System.currentTimeMillis() - time

    val minutes = diff / (1000 * 60)
    val hours = minutes / 60
    val days = hours / 24

    return when {
        minutes < 60 -> "$minutes min ago"
        hours < 24 -> "$hours hr ago"
        else -> "$days days ago"
    }
}

@Preview
@Composable
fun ShowPostCard(){

}