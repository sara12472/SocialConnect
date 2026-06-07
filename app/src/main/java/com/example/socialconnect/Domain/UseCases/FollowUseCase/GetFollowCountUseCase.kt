package com.example.socialconnect.Domain.UseCases.FollowUseCase

import com.example.socialconnect.Domain.Repository.FollowRepository
import javax.inject.Inject

class GetFollowersCountUseCase @Inject constructor(
    private val repository: FollowRepository
) {
    operator fun invoke(userId: String) =
        repository.getFollowersCount(userId)
}