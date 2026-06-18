package com.example.socialconnect.Domain.UseCases.PostUseCase

import com.example.socialconnect.Domain.Repository.PostRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String) {
        repository.deletePost(postId)
    }
}