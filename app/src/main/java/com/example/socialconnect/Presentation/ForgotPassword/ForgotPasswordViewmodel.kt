package com.example.socialconnect.Presentation.ForgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Domain.UseCases.ForgetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ForgotPasswordViewmodel @Inject constructor(
    private val forgetPasswordUseCase: ForgetPasswordUseCase
): ViewModel() {
    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state.asStateFlow()

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(
            email = value,
            errorMessage = null,
            successMessage = null
        )
    }

    fun onSendClick() {

        sendResetEmail(_state.value.email)
    }

    private fun sendResetEmail(email: String) {

        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null,
                successMessage = null
            )

            when (
                val result = forgetPasswordUseCase(email)
            ) {

                is Resource.Success -> {

                    _state.value = _state.value.copy(
                        isLoading = false,
                        successMessage = "Reset link sent to your email"
                    )
                }

                is Resource.Error -> {

                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }

                else -> Unit
            }
        }
    }
}