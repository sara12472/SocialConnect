package com.example.socialconnect.Domain.UseCases.UserUsecase

import com.example.socialconnect.Data.Model.User
import com.example.socialconnect.Domain.Repository.EditProfileRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val repository: EditProfileRepository
) {
    suspend operator fun invoke(query: String): List<User> {
        if (query.isBlank()) return emptyList()

        return repository.searchUsers(query.trim())
    }
}