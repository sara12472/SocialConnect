package com.example.socialconnect.Navigation

import NotificationScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.socialconnect.Presentation.AuthScreen.AuthScreen
import com.example.socialconnect.Presentation.ChatScreen.ChatScreen
import com.example.socialconnect.Presentation.ChatScreen.ConversationScreen

import com.example.socialconnect.Presentation.CreatePost.CreatePostScreen
import com.example.socialconnect.Presentation.EditProfileScreen.EditProfileScreen
import com.example.socialconnect.Presentation.ForgotPassword.ForgetPasswordScreen
import com.example.socialconnect.Presentation.HomeScreen.HomeScreen
import com.example.socialconnect.Presentation.LoginSCreen.LoginScreen
import com.example.socialconnect.Presentation.ProfileScreen.ProfileScreen
import com.example.socialconnect.Presentation.SavedPost.SavedPostScreen
import com.example.socialconnect.Presentation.SettingScreen.SettingsScreen
import com.example.socialconnect.Presentation.SignUpScreen.SignUpScreen
import com.example.socialconnect.Presentation.SplashScreen.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController)
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
        composable(Screen.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(Screen.ProfileScreen.route){
          ProfileScreen(navController)
        }
        composable(Screen.EditProfileScreen.route){
            EditProfileScreen(navController)
        }
        composable(Screen.SettingScreen.route){
            SettingsScreen(navController)
        }
        composable(Screen.NotificationScreen.route){
           NotificationScreen(navController)
        }
        composable(Screen.SavedPostScreen.route){
          SavedPostScreen(navController)
        }
        composable(
            route = Screen.ConversationScreen.route,
            arguments = listOf(
                navArgument("otherUserId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val otherUserId =
                backStackEntry.arguments
                    ?.getString("otherUserId") ?: ""

            ConversationScreen(
                otherUserId = otherUserId,
                onBackClick ={navController.popBackStack()}
            )
        }
        composable(Screen.ChatScreen.route){
          ChatScreen(  onChatClick = { otherUserId ->

              navController.navigate(
                  Screen.ConversationScreen.createRoute(otherUserId)
              )
          }, navController = navController)
        }
        composable(
            route = "create_post?mediaUri={mediaUri}&mediaType={mediaType}&postId={postId}",
            arguments = listOf(
                navArgument("mediaUri") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("mediaType") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("postId") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { backStackEntry ->

            val mediaUri =
                backStackEntry.arguments?.getString("mediaUri") ?: ""

            val mediaType =
                backStackEntry.arguments?.getString("mediaType") ?: ""

            CreatePostScreen(
                navController = navController,
                mediaUri = mediaUri,
                mediaType = mediaType,
                postId = backStackEntry.arguments?.getString("postId")
            )
        }
    }
}