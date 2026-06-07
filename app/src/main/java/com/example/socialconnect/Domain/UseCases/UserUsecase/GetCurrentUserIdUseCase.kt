package com.example.socialconnect.Domain.UseCases.UserUsecase

import com.example.socialconnect.Domain.Repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): String? {
        return authRepository.getCurrentUserId()
    }
}