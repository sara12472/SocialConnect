package com.example.socialconnect.Presentation.SignUpScreen

data class SignUpState(
    val firstName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false
)
