package com.example.socialconnect.Presentation.Notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.NotificationUseCase.GetNotificationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationState())
    val state = _state.asStateFlow()

    init {
        loadNotifications()
    }

    private fun loadNotifications() {

        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch

            getNotificationsUseCase(userId).collect { list ->

                _state.value = _state.value.copy(
                    notifications = list,
                    isLoading = false
                )
            }
        }
    }
}