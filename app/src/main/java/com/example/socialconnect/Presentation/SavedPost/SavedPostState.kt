package com.example.socialconnect.Presentation.SavedPost

import com.example.socialconnect.Data.Model.Post

data class SavedState(
    val savedPosts: List<Post> = emptyList(),
    val selectedTab: Int = 0,
    val isLoading: Boolean = false
)