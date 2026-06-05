package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.FollowRepository
import javax.inject.Inject


class UnfollowUserUseCase @Inject constructor(
    private val repository: FollowRepository
) {
    suspend operator fun invoke(
        currentUserId: String,
        targetUserId: String
    ) {
        repository.unfollowUser(currentUserId, targetUserId)
    }
}