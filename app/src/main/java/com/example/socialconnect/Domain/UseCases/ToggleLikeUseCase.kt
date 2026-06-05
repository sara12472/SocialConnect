package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.PostRepository
import javax.inject.Inject

class ToggleLikeUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String, userId: String) {
        repository.toggleLike(postId, userId)
    }
}