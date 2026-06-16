package com.example.socialconnect.Domain.UseCases.ChatUseCase

import com.example.socialconnect.Domain.Repository.MessageRepository
import javax.inject.Inject

class GetOrCreateChatUseCase @Inject constructor(
    private val checkChatExistsUseCase: CheckChatExistsUseCase,
    private val createChatUseCase: CreateChatUseCase
) {
    suspend operator fun invoke(
        currentUserId: String,
        otherUserId: String
    ): String {

        val existingChatId = checkChatExistsUseCase(
            currentUserId,
            otherUserId
        )

        return existingChatId ?: createChatUseCase(
            currentUserId,
            otherUserId
        )
    }
}