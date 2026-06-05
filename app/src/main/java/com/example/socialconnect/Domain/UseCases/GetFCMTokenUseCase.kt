package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.EditProfileRepository
import com.example.socialconnect.Domain.Repository.FcmRepository
import javax.inject.Inject

class GetFcmTokenUseCase @Inject constructor(
    private val repository: FcmRepository
) {
    suspend operator fun invoke(): String {
        return repository.getToken()
    }
}