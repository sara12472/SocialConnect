package com.example.socialconnect.Navigation

import NotificationScreen
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.socialconnect.Component.FloatingBottomBar
import com.example.socialconnect.Presentation.AuthScreen.AuthScreen
import com.example.socialconnect.Presentation.ChatScreen.ChatScreen
import com.example.socialconnect.Presentation.ChatScreen.ConversationScreen
import com.example.socialconnect.Presentation.CreatePost.CreatePostScreen
import com.example.socialconnect.Presentation.EditProfileScreen.EditProfileScreen
import com.example.socialconnect.Presentation.ForgotPassword.ForgetPasswordScreen
import com.example.socialconnect.Presentation.HomeScreen.HomeScreen
import com.example.socialconnect.Presentation.SearchScreen.SearchScreen
import com.example.socialconnect.Presentation.LoginSCreen.LoginScreen
import com.example.socialconnect.Presentation.ProfileScreen.ProfileScreen
import com.example.socialconnect.Presentation.SavedPost.SavedPostScreen
import com.example.socialconnect.Presentation.SettingScreen.SettingsScreen
import com.example.socialconnect.Presentation.SignUpScreen.SignUpScreen
import com.example.socialconnect.Presentation.SplashScreen.SplashScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val appViewModel: AppViewModel = hiltViewModel()

    val currentUserId by appViewModel.currentUserId.collectAsState()
    val profileImage by appViewModel.profileImage.collectAsState()

    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    val bottomBarRoutes = listOf(
        Screen.HomeScreen.route,
        Screen.ChatScreen.route,
        Screen.SearchScreen.route,
        Screen.SettingScreen.route
    )
    val showBottomBar = currentRoute in bottomBarRoutes || currentRoute == Screen.ProfileScreen.route

    Box(modifier = Modifier.fillMaxSize()) {

        NavHost(
            navController,
            startDestination = Screen.SplashScreen.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screen.SplashScreen.route) {
                SplashScreen(navController)
            }
            composable(Screen.LoginScreen.route) {
                LoginScreen(navController, appViewModel)
            }
            composable(Screen.SignUpScreen.route) {
                SignUpScreen(navController)
            }
            composable(Screen.AuthScreen.route) {
                AuthScreen(navController)
            }
            composable(Screen.ForgetPasswordScreen.route) {
                ForgetPasswordScreen(navController)
            }
            composable(Screen.HomeScreen.route) {
                HomeScreen(navController)
            }
            composable(
                route = Screen.ProfileScreen.route,
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) { entry ->
                val userId = entry.arguments?.getString("userId") ?: ""
                ProfileScreen(navController = navController, userId = userId)
            }
            composable(Screen.EditProfileScreen.route) {
                EditProfileScreen(navController)
            }
            composable(Screen.SettingScreen.route) {
                SettingsScreen(navController, appViewModel)
            }
            composable(Screen.NotificationScreen.route) {
                NotificationScreen(navController)
            }
            composable(route = Screen.SearchScreen.route) {
                SearchScreen(navController = navController)
            }
            composable(Screen.SavedPostScreen.route) {
                SavedPostScreen(navController)
            }
            composable(
                route = Screen.ConversationScreen.route,
                arguments = listOf(navArgument("otherUserId") { type = NavType.StringType })
            ) { backStackEntry ->
                val otherUserId = backStackEntry.arguments?.getString("otherUserId") ?: ""
                ConversationScreen(
                    otherUserId = otherUserId,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.ChatScreen.route) {
                ChatScreen(
                    onChatClick = { otherUserId ->
                        navController.navigate(Screen.ConversationScreen.createRoute(otherUserId))
                    },
                    navController = navController
                )
            }
            composable(
                route = "create_post?mediaUri={mediaUri}&mediaType={mediaType}&postId={postId}",
                arguments = listOf(
                    navArgument("mediaUri") { type = NavType.StringType; defaultValue = "" },
                    navArgument("mediaType") { type = NavType.StringType; defaultValue = "" },
                    navArgument("postId") { type = NavType.StringType; defaultValue = ""; nullable = true }
                )
            ) { backStackEntry ->
                val mediaUri = backStackEntry.arguments?.getString("mediaUri") ?: ""
                val mediaType = backStackEntry.arguments?.getString("mediaType") ?: ""
                CreatePostScreen(
                    navController = navController,
                    mediaUri = mediaUri,
                    mediaType = mediaType,
                    postId = backStackEntry.arguments?.getString("postId")
                )
            }
        }

        if (showBottomBar) {
            FloatingBottomBar(
                selectedIndex = bottomBarRoutes.indexOf(currentRoute).coerceAtLeast(0),
                profileImage = profileImage,
                modifier = Modifier

                    .padding(bottom = 20.dp, start = 16.dp, end = 16.dp).align(alignment = Alignment.BottomCenter),

                onItemSelected = { index ->
                    val route = bottomBarRoutes[index]
                    navController.navigate(route) {
                        popUpTo(Screen.HomeScreen.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onProfileClick = {
                    if (currentUserId.isNotEmpty()) {
                        navController.navigate(Screen.ProfileScreen.createRoute(currentUserId))
                    }
                }
            )
        }
    }
}