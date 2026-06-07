package com.example.socialconnect.Domain.UseCases.PostUseCase

import com.example.socialconnect.Domain.Repository.SavedPostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedPostsUseCase @Inject constructor(
    private val repository: SavedPostRepository
) {
    operator fun invoke(userId: String): Flow<List<String>> {
        return repository.getSavedPostIds(userId)
    }
}
