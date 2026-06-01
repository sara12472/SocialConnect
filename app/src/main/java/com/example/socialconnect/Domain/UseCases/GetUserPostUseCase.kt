package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.PostRepository
import jakarta.inject.Inject

class GetUserPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {

    operator fun invoke(uid: String) =
        repository.getUserPosts(uid)
}