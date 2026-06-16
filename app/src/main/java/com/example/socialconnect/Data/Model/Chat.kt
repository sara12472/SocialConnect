package com.example.socialconnect.Data.Model

data class Chat(
    val chatId: String = "",
    val participants: List<String> = emptyList(),
    val lastMessage: String = "",
    val lastMessageTime: Long = 0,
    val otherUserId: String = "",
    val otherUserName: String = "",
    val otherUserProfile: String = ""
)
