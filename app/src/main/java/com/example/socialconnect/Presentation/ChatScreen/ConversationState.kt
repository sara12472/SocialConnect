package com.example.socialconnect.Presentation.ChatScreen

import com.example.socialconnect.Data.Model.Message

data class ConversationState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val chatId: String = "",
    val currentUserId: String = "",

    val otherUserName: String = "",
    val otherUsername: String = "",
    val otherUserProfile: String = "",

    val error: String = "",
    val otherUserId: String = ""


)
