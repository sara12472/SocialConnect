package com.example.socialconnect.Presentation.SignUpScreen

data class SignUpState(
    val step: Int = 1,

    val userName:String="",
    val name: String="",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
