package com.example.socialconnect.Presentation.Notification

import com.example.socialconnect.Data.Model.AppNotification

data class NotificationState(
    val notifications: List<AppNotification> = emptyList(),
    val isLoading: Boolean = false
)