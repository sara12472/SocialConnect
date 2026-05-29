package com.example.socialconnect.Domain.UseCases


import com.example.socialconnect.Domain.Repository.EditProfileRepository
import jakarta.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: EditProfileRepository
) {

    suspend operator fun invoke(uid: String) =
        repository.getUser(uid)
}