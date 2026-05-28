package com.example.socialconnect.Presentation.LoginSCreen

data class LoginState(

    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,

    val error: String? = null


)
