package com.example.socialconnect.Presentation.HomeScreen


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.socialconnect.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialconnect.Data.Model.dummyStories
import com.example.socialconnect.Navigation.Screen
import com.example.socialconnect.Presentation.HomeScreen.Component.BottomBar
import com.example.socialconnect.Presentation.HomeScreen.Component.HomeTopBar
import com.example.socialconnect.Presentation.HomeScreen.Component.PostCard
import com.example.socialconnect.Presentation.HomeScreen.Component.ProfileFloatingButton
import com.example.socialconnect.Presentation.HomeScreen.Component.StoryRow

@Composable
fun HomeScreen( navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),

) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    val listState = rememberLazyListState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        uri?.let {

            val mimeType = context.contentResolver.getType(it)

            val type =
                if (mimeType?.startsWith("video") == true) "video"
                else "image"

            navController.navigate(
                Screen.CreatePostScreen.route +
                        "?mediaUri=${Uri.encode(it.toString())}" +
                        "&mediaType=$type"
            )
        }
    }
    LaunchedEffect(Unit) {
        viewModel.refreshUser()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),


            ) {


                item {
                    HomeTopBar(
                        onAddClick = { launcher.launch("*/*") },
                        onHeartClick = { }
                    )
                }

                // Stories
                item {
                    StoryRow(stories = dummyStories)
                }

                // Posts
                itemsIndexed(state.posts) { index, post ->

                    val layoutInfo = listState.layoutInfo

                    val centerIndex =
                        listState.layoutInfo.visibleItemsInfo
                            .minByOrNull { item ->
                                kotlin.math.abs(item.offset)
                            }
                            ?.index

                    val isVisible = centerIndex == index + 2

                    PostCard(
                        post = post,
                        isVisible = isVisible,
                        onUserClick = {
                            navController.navigate(
                                Screen.ProfileScreen.createRoute(post.userId)
                            )
                        }
                    )
                }
            }
        }

        // 🔥 FLOATING BOTTOM BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 25.dp, vertical = 12.dp)

            ,

            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row() {
                BottomBar(
                    selectedIndex = state.selectedTab,
                    onItemSelected = viewModel::onTabSelected,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Spacer(modifier = Modifier.width(5.dp))

                Row() {
                    ProfileFloatingButton(
                        image = state.profileImage,
                        onClick = { navController.navigate(
                            Screen.ProfileScreen.createRoute(
                                state.currentUserId
                            )
                        )}
                    )
                }


            }
        }
    }
}
@Preview()
@Composable
fun ShowHomeScreen(){
    val viewModel: HomeViewModel = hiltViewModel()
    val navController= rememberNavController()
    HomeScreen(navController,viewModel)

}

