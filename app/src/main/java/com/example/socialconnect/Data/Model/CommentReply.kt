package com.example.socialconnect.Data.Model

data class CommentReply(
    val replyId: String = "",
    val commentId: String = "",
    val postId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userProfile: String = "",
    val text: String = "",
    val timestamp: Long = 0L
)
