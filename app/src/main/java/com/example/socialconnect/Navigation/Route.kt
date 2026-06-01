package com.example.socialconnect.Navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("SplashScreen")
    object LoginScreen : Screen("LoginScreen")
    object SignUpScreen : Screen("SignUpScreen")
    object AuthScreen : Screen("AuhScreen")
    object ForgetPasswordScreen : Screen("ForgotPasswordScreen")
    object HomeScreen : Screen("HomeScreen")
    object ProfileScreen : Screen("profile/{userId}") {

        fun createRoute(userId: String) = "profile/$userId"
    }    object EditProfileScreen : Screen("EditProfileScreen")
    object CreatePostScreen : Screen("create_post")






}