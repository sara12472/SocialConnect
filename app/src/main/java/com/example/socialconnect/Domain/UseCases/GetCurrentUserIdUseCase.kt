package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.AuthRepository
import jakarta.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): String? {
        return authRepository.getCurrentUserId()
    }
}