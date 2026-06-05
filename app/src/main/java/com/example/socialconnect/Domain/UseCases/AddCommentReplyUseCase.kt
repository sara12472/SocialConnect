package com.example.socialconnect.Domain.UseCases



import com.example.socialconnect.Data.Model.CommentReply
import com.example.socialconnect.Domain.Repository.CommentRepository
import javax.inject.Inject

class AddReplyUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(reply: CommentReply) {
        repository.addReply(reply)
    }
}