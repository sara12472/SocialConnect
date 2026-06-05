package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Data.Model.Post
import com.example.socialconnect.Domain.Repository.PostRepository
import javax.inject.Inject

class GetPostByIdsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(ids: List<String>): List<Post> {
        return repository.getPostsByIds(ids)
    }
}