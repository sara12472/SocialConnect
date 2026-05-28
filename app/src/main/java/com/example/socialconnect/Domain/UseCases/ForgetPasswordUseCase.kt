package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Domain.Repository.AuthRepository
import jakarta.inject.Inject


class ForgetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String
    ): Resource<Boolean> {

        if (email.isBlank()) {
            return Resource.Error("Email is required")
        }

        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
        ) {
            return Resource.Error("Invalid email format")
        }

        return repository.resetPassword(email)
    }
}