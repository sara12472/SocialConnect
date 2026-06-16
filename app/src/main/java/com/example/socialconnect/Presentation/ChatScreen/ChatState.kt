package com.example.socialconnect.Presentation.ChatScreen



import com.example.socialconnect.Data.Model.Chat

data class ChatState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val currentUserName: String = "",

    val otherUserName: String = "",
    val otherUserProfile: String = "",
    val currentUserId: String = "",
)
