package com.example.socialconnect.Data.Model

data class Post(
    val postId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userProfile: String = "",
    val caption: String = "",
    val mediaUrl: String = "",
    val mediaType: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val likes: Int = 0,
    val likedBy: List<String> = emptyList(),
    val totalCommentsCount: Int = 0

)
