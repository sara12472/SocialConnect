package com.example.socialconnect.Presentation.SignUpScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Domain.Repository.AuthRepository
import com.example.socialconnect.Domain.UseCases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel  @Inject constructor(private val signupUseCase: SignUpUseCase) : ViewModel(

) {
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    fun onNameChange(value: String) {
        _state.value = _state.value.copy(name = value)
    }
    fun onUserNameChange(value: String) {
        _state.value = _state.value.copy(userName = value)
    }

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    fun onConfirmPasswordChange(value: String) {
        _state.value = _state.value.copy(confirmPassword = value)
    }

    fun nextStep() {
        if (_state.value.step < 4) {
            _state.value = _state.value.copy(step = _state.value.step + 1)
        }
    }

    fun previousStep() {
        if (_state.value.step > 1) {
            _state.value = _state.value.copy(step = state.value.step - 1)
        }
    }
    fun setError(message: String) {
        _state.value = _state.value.copy(error = message)
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    fun signup() {

        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true)

            when (
                val result = signupUseCase(
                    name = state.value.name,
                    username = state.value.userName,
                    email = state.value.email,
                    password = state.value.password,
                    confirmPassword = state.value.confirmPassword
                )
            ) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        success = true
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                else -> Unit
            }
        }
    }
            }
