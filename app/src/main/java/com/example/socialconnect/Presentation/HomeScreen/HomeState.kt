package com.example.socialconnect.Presentation.HomeScreen

import com.example.socialconnect.Data.Model.Post
import com.example.socialconnect.Data.Model.Story

data class HomeState(

    val stories: List<Story> = emptyList(),
    val posts: List<Post> = emptyList(),

    val profileImage: String = "",

    val selectedTab: Int = 0,
    val currentUserId: String = "",
)