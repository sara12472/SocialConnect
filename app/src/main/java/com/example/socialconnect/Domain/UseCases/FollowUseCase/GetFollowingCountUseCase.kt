package com.example.socialconnect.Domain.UseCases.FollowUseCase

import com.example.socialconnect.Domain.Repository.FollowRepository
import javax.inject.Inject

class GetFollowingCountUseCase @Inject constructor(
    private val repository: FollowRepository
) {
    operator fun invoke(userId: String) =
        repository.getFollowingCount(userId)
}