package com.example.socialconnect.Data.Model

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val name: String = "",
    val bio: String = "",
    val profileImage: String = "",
    val notificationsEnabled: Boolean = true,
    val fcmToken: String = ""
)