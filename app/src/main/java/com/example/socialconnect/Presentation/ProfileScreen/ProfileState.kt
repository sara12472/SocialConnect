package com.example.socialconnect.Presentation.ProfileScreen

data class ProfileState(
    val name: String = "",
    val username: String = "",
    val bio: String = "",
    val profileImage: String = "",

    val selectedTab: Int = 0
)