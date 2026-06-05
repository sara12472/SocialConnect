package com.example.socialconnect.Data.Model
data class AppNotification(
    val notificationId: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val senderProfile: String = "",
    val receiverId: String = "",
    val postId: String = "",
    val type: String = "",
    val timestamp: Long = System.currentTimeMillis()
)