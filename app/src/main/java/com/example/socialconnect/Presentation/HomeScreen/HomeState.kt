package com.example.socialconnect.Presentation.HomeScreen

import com.example.socialconnect.Data.Post
import com.example.socialconnect.Data.Story

data class HomeState(

    val stories: List<Story> = emptyList(),

    val posts: List<Post> = emptyList(),

    val selectedTab: Int = 0
)