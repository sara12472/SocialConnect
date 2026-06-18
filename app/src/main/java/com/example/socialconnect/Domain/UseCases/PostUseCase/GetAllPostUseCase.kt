package com.example.socialconnect.Domain.UseCases.PostUseCase

import com.example.socialconnect.Domain.Repository.PostRepository
import javax.inject.Inject


class GetAllPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {

    operator fun invoke() = repository.getAllPosts()
}