package com.example.socialconnect.Domain.UseCases.ChatUseCase

import com.example.socialconnect.Domain.Repository.MessageRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repo: MessageRepository
) {
    suspend operator fun invoke(chatId: String) =
        repo.getMessages(chatId)
}