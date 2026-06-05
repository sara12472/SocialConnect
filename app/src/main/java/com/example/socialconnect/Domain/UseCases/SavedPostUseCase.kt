package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.SavedPostRepository

class SavePostUseCase(
    private val repository: SavedPostRepository
) {
    suspend operator fun invoke(postId: String) {
        repository.savePost(postId)
    }
}