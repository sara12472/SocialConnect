package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() {
        repository.logout()
    }
}