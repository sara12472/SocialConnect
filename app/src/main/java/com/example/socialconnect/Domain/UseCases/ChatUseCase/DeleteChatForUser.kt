package com.example.socialconnect.Domain.UseCases.ChatUseCase

import com.example.socialconnect.Domain.Repository.MessageRepository
import javax.inject.Inject

class DeleteChatForUserUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(chatId: String, userId: String) {
        repository.deleteChatForUser(chatId, userId)
    }
}