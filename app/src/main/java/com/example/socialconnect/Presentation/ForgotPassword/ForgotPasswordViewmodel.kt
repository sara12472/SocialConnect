package com.example.socialconnect.Presentation.ForgotPassword

import androidx.lifecycle.ViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForgotPasswordViewmodel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state.asStateFlow()

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(email = value)
    }

    fun onSendClick() {
        val email = _state.value.email

        if (email.isBlank()) {
            _state.value = _state.value.copy(
                errorMessage = "Email cannot be empty"
            )
            return
        }

        sendResetEmail(email)
    }

    private fun sendResetEmail(email: String) {


    }
}