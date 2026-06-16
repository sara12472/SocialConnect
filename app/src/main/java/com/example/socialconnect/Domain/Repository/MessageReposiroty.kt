package com.example.socialconnect.Domain.Repository

import com.example.socialconnect.Data.Model.Chat
import com.example.socialconnect.Data.Model.Message

interface MessageRepository {
    suspend fun getMessages(chatId: String): List<Message>

    suspend fun sendMessage(
        chatId: String,
        message: Message
    )

    suspend fun getOrCreateChat(
        currentUserId: String,
        otherUserId: String
    ): String
    suspend fun getChats(
        currentUserId: String
    ): List<Chat>
    suspend fun checkChatExists(
        currentUserId: String,
        otherUserId: String
    ): String?
    suspend fun createChat(
        currentUserId: String,
        otherUserId: String
    ): String
}