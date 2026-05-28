package com.example.socialconnect.Presentation.AuthScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Domain.Repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel  @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val authRepository: AuthRepository,
): ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    private val _googleSignInIntent = MutableStateFlow<android.content.Intent?>(null)
    val googleSignInIntent = _googleSignInIntent.asStateFlow()

    fun signInWithGoogle() {
        _googleSignInIntent.value = googleSignInClient.signInIntent
    }

    fun handleGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            when (val result = authRepository.signInWithGoogle(idToken)) {

                is Resource.Success -> {
                    _state.value = _state.value.copy(success = true)
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(error = result.message)
                }

                else -> {}
            }
        }
    }
}