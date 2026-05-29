package com.example.socialconnect.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.socialconnect.Presentation.AuthScreen.AuthScreen
import com.example.socialconnect.Presentation.EditProfileScreen.EditProfileScreen
import com.example.socialconnect.Presentation.ForgotPassword.ForgetPasswordScreen
import com.example.socialconnect.Presentation.HomeScreen.HomeScreen
import com.example.socialconnect.Presentation.LoginSCreen.LoginScreen
import com.example.socialconnect.Presentation.ProfileScreen.ProfileScreen
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
    }
}