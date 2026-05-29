package com.example.socialconnect.Presentation.ProfileScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialconnect.Data.Model.dummyPosts
import com.example.socialconnect.Navigation.Screen
import com.example.socialconnect.Presentation.ProfileScreen.Component.ProfileHeader
import com.example.socialconnect.Presentation.ProfileScreen.Component.ProfileStatsSection
import com.example.socialconnect.Presentation.ProfileScreen.Component.ProfileTabSection
import com.example.socialconnect.Presentation.ProfileScreen.Component.ProfileTopBar

@Composable
fun ProfileScreen(  navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),

) {

    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadUser()
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

            // 🔵 TOP BAR
            ProfileTopBar(userName = state.username, onEditClick = {navController.navigate(Screen.EditProfileScreen.route)}, onBackClick = {
                navController.popBackStack()
            })

            ProfileHeader(profileImage = state.profileImage, userName = state.name, bio =state.bio)

            Spacer(modifier = Modifier.height(12.dp))

            // 🔵 STATS
            ProfileStatsSection(
                posts = 12,
                followers = 300,
                following = 180
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 🔵 TABS
            ProfileTabSection(
                selectedTab = state.selectedTab,
                onTabSelected = viewModel::onTabSelected
            )

            // 🔵 CONTENT
            if (state.selectedTab == 0) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),

                    contentPadding = PaddingValues(4.dp),

                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    items(dummyPosts) { post ->

                        Image(
                            painter = painterResource(id = post.postImage),
                            contentDescription = "Post",

                            modifier = Modifier
                                .fillMaxWidth()
                                .size(120.dp)
                        )
                    }
                }

            } else {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    androidx.compose.material3.Text(
                        text = "No Videos Yet",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun ShowProfileScreen(){
   val viewModel: ProfileViewModel = hiltViewModel()
    val navController= rememberNavController()

    ProfileScreen(navController,
        viewModel
    )
}
