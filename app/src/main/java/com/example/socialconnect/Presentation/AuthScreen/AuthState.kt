package com.example.socialconnect.Presentation.AuthScreen

data class AuthState(
    val isLoading: Boolean = false,
    val success: Boolean = false,

    val error: String? = null
)
