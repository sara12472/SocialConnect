package com.example.socialconnect.Presentation.ProfileScreen

import com.example.socialconnect.Data.Model.Post

data class ProfileState(
    val name: String = "",
    val username: String = "",
    val bio: String = "",
    val profileImage: String = "",
    val posts: List<Post> = emptyList(),

    val isFollowing: Boolean = false,
    val selectedTab: Int = 0,

    val userId: String = "",
    val currentUserId: String = "",
    val isOwnProfile: Boolean = false

)