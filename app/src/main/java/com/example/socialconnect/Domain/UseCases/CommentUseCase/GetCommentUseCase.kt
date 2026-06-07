package com.example.socialconnect.Domain.UseCases.CommentUseCase

import com.example.socialconnect.Domain.Repository.CommentRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repo: CommentRepository
) {
    operator fun invoke(postId: String) = repo.getComments(postId)
}