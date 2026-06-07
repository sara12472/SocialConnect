package com.example.socialconnect.Domain.UseCases.NotificationUseCase

import com.example.socialconnect.Data.Model.AppNotification
import com.example.socialconnect.Domain.Repository.NotificationRepository
import javax.inject.Inject

class SendNotificationUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(notification: AppNotification) {
        repository.sendNotification(notification)
    }
}