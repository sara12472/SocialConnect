package com.example.socialconnect.Presentation.ForgotPassword

data class ForgotPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)