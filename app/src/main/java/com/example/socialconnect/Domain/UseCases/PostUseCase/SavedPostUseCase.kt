package com.example.socialconnect.Domain.UseCases.PostUseCase

import com.example.socialconnect.Domain.Repository.SavedPostRepository

class SavePostUseCase(
    private val repository: SavedPostRepository
) {
    suspend operator fun invoke(postId: String) {
        repository.savePost(postId)
    }
}