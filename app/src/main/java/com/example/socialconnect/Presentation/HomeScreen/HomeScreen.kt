package com.example.socialconnect.Presentation.HomeScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.socialconnect.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialconnect.Data.dummyPosts
import com.example.socialconnect.Data.dummyStories
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(

            topBar = {
                HomeTopBar(
                    onAddClick = { viewModel.onAddClick() },
                    onHeartClick = { viewModel.onHeartClick() }
                )
            },

            containerColor = MaterialTheme.colorScheme.background

        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                StoryRow(
                    stories = dummyStories
                )

                LazyColumn {

                    items(dummyPosts) { post ->

                        PostCard(post = post)
                    }
                }
            }
        }

        // 🔥 FLOATING BOTTOM BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 25.dp, vertical = 12.dp),
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
                        image = R.drawable.profile,
                        onClick = {navController.navigate(Screen.ProfileScreen.route)}
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

