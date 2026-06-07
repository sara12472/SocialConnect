package com.example.socialconnect.Domain.UseCases.authUseCase

import android.util.Patterns
import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Domain.Repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(
        name: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Resource<Boolean> {

        if (
            name.isBlank() ||
            username.isBlank() ||
            email.isBlank() ||
            password.isBlank()
        ) {
            return Resource.Error("All fields are required")
        }

        if (password.length < 6) {
            return Resource.Error("Password must be at least 6 characters")
        }

        if (password != confirmPassword) {
            return Resource.Error("Passwords do not match")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Resource.Error("Invalid email format")
        }

        return repository.signup(
            name = name,
            username = username,
            email = email,
            password = password
        )
    }

}