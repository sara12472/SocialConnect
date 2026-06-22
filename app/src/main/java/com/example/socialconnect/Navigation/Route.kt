package com.example.socialconnect.Navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("SplashScreen")
    object LoginScreen : Screen("LoginScreen")
    object SignUpScreen : Screen("SignUpScreen")
    object SavedPostScreen : Screen("SavedPostScreen")
    object AppNavigationScreen : Screen("AppNavigationScreen")
    object AuthScreen : Screen("AuhScreen")
    object ForgetPasswordScreen : Screen("ForgotPasswordScreen")
    object HomeScreen : Screen("HomeScreen")
    object ChatScreen : Screen("ChatScreen")
    object SearchScreen : Screen("SearchScreen")
    object MainScreen : Screen("MainScreen")



    object ConversationScreen :
        Screen("conversation_screen/{otherUserId}") {

        fun createRoute(otherUserId: String) =
            "conversation_screen/$otherUserId"
    }
    object ProfileScreen : Screen("profile/{userId}") {

        fun createRoute(userId: String?) = "profile/$userId"
    }
    object EditProfileScreen : Screen("EditProfileScreen")
    object CreatePostScreen : Screen("create_post")
    object SettingScreen : Screen("SettingScreen")
    object NotificationScreen : Screen("NotificationScreen")
}
