package com.example.socialconnect.Domain.UseCases



import com.example.socialconnect.Data.Model.CommentReply
import com.example.socialconnect.Domain.Repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepliesUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    operator fun invoke(
        postId: String,
        commentId: String
    ): Flow<List<CommentReply>> {
        return repository.getReplies(postId, commentId)
    }
}