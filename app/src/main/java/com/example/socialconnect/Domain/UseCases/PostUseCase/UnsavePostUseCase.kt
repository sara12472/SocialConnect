package com.example.socialconnect.Domain.UseCases.PostUseCase

import com.example.socialconnect.Domain.Repository.SavedPostRepository
import javax.inject.Inject

class UnSavePostUseCase @Inject constructor(
    private val repository: SavedPostRepository
) {
    suspend operator fun invoke(postId: String) {
        repository.unSavePost(postId)
    }
}