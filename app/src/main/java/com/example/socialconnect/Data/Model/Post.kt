package com.example.socialconnect.Data.Model

data class Post(
    val postId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userProfile: String = "",
    val caption: String = "",
    val mediaUrl: String = "",
    val mediaType: String = "", // image or video
    val timestamp: Long = System.currentTimeMillis()
)
