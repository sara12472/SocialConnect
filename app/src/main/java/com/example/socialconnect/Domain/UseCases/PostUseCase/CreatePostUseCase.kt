package com.example.socialconnect.Domain.UseCases.PostUseCase

import com.example.socialconnect.Data.Model.Post
import com.example.socialconnect.Domain.Repository.PostRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        post: Post
    ) {

        repository.createPost(post)
    }

}