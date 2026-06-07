package com.example.socialconnect.Domain.UseCases.NotificationUseCase

import com.example.socialconnect.Domain.Repository.AuthRepository
import javax.inject.Inject

class UpdateNotificationSettingUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(userId: String, enabled: Boolean) {
        repository.updateNotificationSetting(userId, enabled)
    }
}