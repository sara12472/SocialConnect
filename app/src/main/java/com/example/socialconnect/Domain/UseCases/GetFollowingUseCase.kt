package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.FollowRepository
import javax.inject.Inject

class GetFollowingUsersUseCase @Inject constructor(
    private val repository: FollowRepository
) {

    operator fun invoke(userId: String) =
        repository.getFollowingUsers(userId)
}