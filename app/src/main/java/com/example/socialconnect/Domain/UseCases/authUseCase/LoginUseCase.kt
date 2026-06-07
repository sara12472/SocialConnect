package com.example.socialconnect.Domain.UseCases.authUseCase

import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Domain.Repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(val repository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Resource<Boolean> {

        if (
            email.isBlank() ||
            password.isBlank()
        ) {
            return Resource.Error("All fields are required")
        }

        if (password.length < 6) {
            return Resource.Error(
                "Password must be at least 6 characters"
            )
        }

        return repository.login(
            email = email,
            password = password
        )
    }

}