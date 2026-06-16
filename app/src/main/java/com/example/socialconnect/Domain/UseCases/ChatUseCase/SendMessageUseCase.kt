package com.example.socialconnect.Domain.UseCases.ChatUseCase

import com.example.socialconnect.Data.Model.Message
import com.example.socialconnect.Domain.Repository.MessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repo: MessageRepository
) {
    suspend operator fun invoke(
        chatId: String,
        message: Message
    ) = repo.sendMessage(chatId, message)
}