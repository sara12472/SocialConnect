package com.example.socialconnect.Navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("SplashScreen")
    object LoginScreen : Screen("LoginScreen")
    object SignUpScreen : Screen("SignUpScreen")
    object AuthScreen : Screen("AuhScreen")
    object ForgetPasswordScreen : Screen("ForgotPasswordScreen")
    object HomeScreen : Screen("HomeScreen")
    object ProfileScreen : Screen("ProfileScreen")
    object EditProfileScreen : Screen("EditProfileScreen")





}