package com.example.socialconnect.Domain.UseCases.FollowUseCase

import com.example.socialconnect.Domain.Repository.FollowRepository
import javax.inject.Inject

class IsFollowingUseCase @Inject constructor(
    private val repository: FollowRepository
) {
    suspend operator fun invoke(
        currentUserId: String,
        targetUserId: String
    ): Boolean {
        return repository.isFollowing(currentUserId, targetUserId)
    }
}