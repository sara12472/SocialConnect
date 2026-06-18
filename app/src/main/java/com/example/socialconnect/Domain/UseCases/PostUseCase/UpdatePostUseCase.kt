package com.example.socialconnect.Domain.UseCases.PostUseCase

import com.example.socialconnect.Domain.Repository.PostRepository
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        postId: String,
        caption: String
    ) {
        repository.updatePost(postId, caption)
    }
}