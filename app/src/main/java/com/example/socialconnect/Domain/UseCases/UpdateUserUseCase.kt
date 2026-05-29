package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Data.Model.User
import com.example.socialconnect.Domain.Repository.AuthRepository
import com.example.socialconnect.Domain.Repository.EditProfileRepository
import jakarta.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: EditProfileRepository
) {
    suspend operator fun invoke(user: User) =
        repository.updateUser(user)
}