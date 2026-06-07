package com.example.socialconnect.Domain.UseCases.authUseCase

import android.util.Patterns
import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Domain.Repository.AuthRepository
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String
    ): Resource<Boolean> {

        if (email.isBlank()) {
            return Resource.Error("Email is required")
        }

        if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
        ) {
            return Resource.Error("Invalid email format")
        }

        return repository.resetPassword(email)
    }
}