package com.example.socialconnect.Domain.Repository

import com.example.socialconnect.Data.Model.Comment
import com.example.socialconnect.Data.Model.CommentReply
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    suspend fun addComment(comment: Comment)

 fun getComments(postId: String): Flow<List<Comment>>
    suspend fun addReply(reply: CommentReply)

    fun getReplies(
        postId: String,
        commentId: String
    ): Flow<List<CommentReply>>
}