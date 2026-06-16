package com.example.socialconnect.Domain.UseCases.ChatUseCase


import com.example.socialconnect.Domain.Repository.MessageRepository
import javax.inject.Inject

class CreateChatUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(
        currentUserId: String,
        otherUserId: String
    ): String {
        return repository.createChat(currentUserId, otherUserId)
    }
}