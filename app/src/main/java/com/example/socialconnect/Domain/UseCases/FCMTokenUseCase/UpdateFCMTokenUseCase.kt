package com.example.socialconnect.Domain.UseCases.FCMTokenUseCase

import com.example.socialconnect.Domain.Repository.EditProfileRepository
import javax.inject.Inject

class UpdateFcmTokenUseCase @Inject constructor(
    private val repository: EditProfileRepository
) {
    suspend operator fun invoke(userId: String, token: String) {
        repository.updateFcmToken(userId, token)
    }
}