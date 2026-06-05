package com.example.socialconnect.Domain.Repository

import com.example.socialconnect.Data.Model.AppNotification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun sendNotification(
        notification: AppNotification
    )

    fun getNotifications(
        userId: String
    ): Flow<List<AppNotification>>
}