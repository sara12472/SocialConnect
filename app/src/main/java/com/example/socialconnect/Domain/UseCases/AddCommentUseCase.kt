package com.example.socialconnect.Domain.UseCases

import com.example.socialconnect.Data.Model.Comment
import com.example.socialconnect.Domain.Repository.CommentRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val repo: CommentRepository
) {
    suspend operator fun invoke(comment: Comment) {
        repo.addComment(comment)
    }
}