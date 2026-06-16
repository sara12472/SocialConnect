package com.example.socialconnect.Presentation.SavedPost

import CustomAppBar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialconnect.Component.VideoThumbnailItem
import com.example.socialconnect.Data.Model.Post

@Composable

fun SavedPostScreen(
    navController: NavController,
    viewModel: SavedViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value



        Scaffold { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                CustomAppBar(
                    title = "Saved",
                    onBackClick = { navController.popBackStack() }
                )

                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Loading...")
                    }
                } else {
                    SavedGrid(
                        posts = state.savedPosts,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedGrid(
    posts: List<Post>,
    modifier: Modifier = Modifier
) {

    if (posts.isEmpty()) {

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No saved posts yet")
        }

        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxSize().padding(horizontal = 5.dp),

    ) {

        items(posts) { post ->

            if (post.mediaType == "image") {

                AsyncImage(
                    model = post.mediaUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(120.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )

            } else {

                VideoThumbnailItem(
                    videoUrl = post.mediaUrl
                )
            }
        }
    }
}