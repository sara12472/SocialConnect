package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.NotificationRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {

    operator fun invoke(
        userId: String
    ) = repository.getNotifications(userId)
}