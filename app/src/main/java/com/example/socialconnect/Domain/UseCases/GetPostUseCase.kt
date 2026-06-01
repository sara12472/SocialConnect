package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Domain.Repository.PostRepository
import jakarta.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {

    operator fun invoke() = repository.getAllPosts()
}