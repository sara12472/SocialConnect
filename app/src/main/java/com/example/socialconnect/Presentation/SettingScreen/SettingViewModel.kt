package com.example.socialconnect.Presentation.SettingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.FCMTokenUseCase.GetFcmTokenUseCase
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetUserUseCase
import com.example.socialconnect.Domain.UseCases.authUseCase.LogoutUseCase
import com.example.socialconnect.Domain.UseCases.FCMTokenUseCase.UpdateFcmTokenUseCase
import com.example.socialconnect.Domain.UseCases.NotificationUseCase.UpdateNotificationSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val updateNotificationSettingUseCase: UpdateNotificationSettingUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    private  val getFcmTokenUseCase: GetFcmTokenUseCase,
    private  val updateFcmTokenUseCase: UpdateFcmTokenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    fun logout() {

        viewModelScope.launch {

            logoutUseCase()

            _state.value = _state.value.copy(
                isLoggedOut = true
            )
        }
    }

    fun toggleNotifications(enabled: Boolean) {

        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch

            updateNotificationSettingUseCase(userId, enabled)

            _state.value = _state.value.copy(
                notificationsEnabled = enabled
            )
        }
    }
    fun loadSettings() {

        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch
            val user = getUserUseCase(userId)

            _state.value = _state.value.copy(
                notificationsEnabled = user.notificationsEnabled
            )
            val token = getFcmTokenUseCase()
            updateFcmTokenUseCase(userId, token)
        }
    }
    fun updateFcmToken() {
        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch

            val token = getFcmTokenUseCase()

            updateFcmTokenUseCase(userId, token)
        }
    }
    fun resetLogoutState() {
        _state.value = _state.value.copy(isLoggedOut = false)
    }


}