package com.example.socialconnect.Presentation.LoginSCreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Domain.Repository.AuthRepository
import com.example.socialconnect.Domain.UseCases.LoginUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.socialconnect.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val loginUseCase: LoginUseCase,
    ) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(password = value)
    }
    fun login() {

        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            when(
                val result = loginUseCase(
                    email = state.value.email,
                    password = state.value.password
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


    fun resetState() {
        _state.value = LoginState()
    }



}