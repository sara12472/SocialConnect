package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.FollowRepository
import jakarta.inject.Inject

class FollowUserUseCase @Inject constructor(
    private val repository: FollowRepository
) {
    suspend operator fun invoke(
        currentUserId: String,
        targetUserId: String
    ) {
        repository.followUser(currentUserId, targetUserId)
    }
}