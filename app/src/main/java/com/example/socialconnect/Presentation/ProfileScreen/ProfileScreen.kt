package com.example.socialconnect.Presentation.ProfileScreen


import android.content.Context
import android.content.Intent
import android.media.browse.MediaBrowser
import android.net.Uri
import com.example.socialconnect.R
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import androidx.media3.common.MediaItem
import com.example.socialconnect.Component.AppButton
import com.example.socialconnect.Component.VideoThumbnailItem
import com.example.socialconnect.Navigation.AppViewModel
import com.example.socialconnect.Navigation.Screen
import com.example.socialconnect.Presentation.ProfileScreen.Component.ProfileHeader
import com.example.socialconnect.Presentation.ProfileScreen.Component.ProfileStatsSection
import com.example.socialconnect.Presentation.ProfileScreen.Component.ProfileTabSection
import com.example.socialconnect.Presentation.ProfileScreen.Component.ProfileTopBar

@Composable
fun ProfileScreen(
    navController: NavController,
  userId: String,
    viewModel: ProfileViewModel = hiltViewModel(),

    ) {



    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
  /* val userId = navController
            .currentBackStackEntry
        ?.arguments
        ?.getString("userId")*/

    LaunchedEffect(userId) {
        if (!userId.isNullOrEmpty()) {
            viewModel.loadUser(userId)
        }
    }


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0)

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 10.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            ProfileTopBar(userName = state.username, onEditClick = {navController.navigate(Screen.EditProfileScreen.route)}, onBackClick = {
                navController.popBackStack()
            })

            ProfileHeader(profileImage = state.profileImage, userName = state.name, bio =state.bio)

            Spacer(modifier = Modifier.height(12.dp))


            ProfileStatsSection(
                posts = state.posts.size,
                followers = state.followersCount,
                following = state.followingCount
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (state.isOwnProfile) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AppButton(
                            text = stringResource(R.string.EditProfile_text),
                            onClick = {
                                navController.navigate(Screen.EditProfileScreen.route)
                            },
                            modifier = Modifier.weight(1f).height(40.dp)
                        )

                        AppButton(
                            text = stringResource(R.string.Share_text),
                            onClick = {
                                shareProfile(
                                    context = context,
                                    username = state.username,
                                    userId = state.userId
                                )
                            },
                            modifier = Modifier.weight(1f).height(40.dp),
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                    }

                } else {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (state.isOwnProfile) {

                                // 👤 OWN PROFILE
                                AppButton(
                                    text = stringResource(R.string.EditProfile_text),
                                    onClick = {
                                        navController.navigate(Screen.EditProfileScreen.route)
                                    },
                                    modifier = Modifier.weight(1f).height(40.dp)
                                )

                                AppButton(
                                    text = stringResource(R.string.Share_text),
                                    onClick = {
                                        shareProfile(
                                            context = context,
                                            username = state.username,
                                            userId = state.userId
                                        )
                                    },
                                    modifier = Modifier.weight(1f).height(40.dp),
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    contentColor = MaterialTheme.colorScheme.onBackground
                                )

                            } else {

                                // 👤 OTHER USER PROFILE
                                AppButton(
                                    text = if (state.isFollowing)
                                        stringResource(R.string.Following_text)
                                    else
                                        stringResource(R.string.Follow_text),
                                    onClick = {
                                        viewModel.onFollowClick(state.userId)
                                    },
                                    modifier = Modifier.weight(1f).height(40.dp)
                                )

                                AppButton(
                                    text = stringResource(R.string.Message_text),
                                    onClick = {
                                        navController.navigate(
                                            "conversation_screen/${state.userId}"
                                        )
                                    },
                                    modifier = Modifier.weight(1f).height(40.dp),
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    contentColor = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))



            ProfileTabSection(
                selectedTab = state.selectedTab,
                onTabSelected = viewModel::onTabSelected
            )


            if (state.selectedTab == 0) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.weight(1f),

                    contentPadding = PaddingValues(0.dp),

                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    items(state.posts.filter { it.mediaType=="image" }) { post ->

                        AsyncImage(
                            model = post.mediaUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(120.dp)
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

            } else {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),

                    contentPadding = PaddingValues(0.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {

                    items(state.posts.filter { it.mediaType == "video" }) { post ->

                        VideoThumbnailItem(videoUrl = post.mediaUrl)
                    }
                }
            }

        }
    }
}



fun shareProfile(
    context: Context,
    username: String,
    userId: String
) {
    val profileLink = "https://socialconnect.app/profile/$userId"

    val shareText = """
        Check out $username's profile 👇
        $profileLink
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(
        Intent.createChooser(intent, "Share Profile")
    )
}
