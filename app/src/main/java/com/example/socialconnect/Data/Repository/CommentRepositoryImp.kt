package com.example.socialconnect.Data.Repository

import com.example.socialconnect.Data.Model.Comment
import com.example.socialconnect.Data.Model.CommentReply
import com.example.socialconnect.Domain.Repository.CommentRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CommentRepository {
    private val postsRef = firestore.collection("posts")

    override suspend fun addComment(comment: Comment) {

        val postRef = postsRef.document(comment.postId)

        postRef.update(
            "totalCommentsCount",
            FieldValue.increment(1)
        )

        postRef.collection("comments")
            .document(comment.commentId)
            .set(comment)
            .await()
    }

    override  fun getComments(postId: String): Flow<List<Comment>> = callbackFlow {

        val listener = firestore.collection("posts")
            .document(postId)
            .collection("comments")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->

                val comments = snapshot?.documents?.mapNotNull {
                    it.toObject(Comment::class.java)
                } ?: emptyList()

                trySend(comments)
            }

        awaitClose { listener.remove() }
    }
    override suspend fun addReply(reply: CommentReply) {

        val postRef = postsRef.document(reply.postId)

        postRef.update(
            "totalCommentsCount",
            FieldValue.increment(1)
        )

        postRef.collection("comments")
            .document(reply.commentId)
            .collection("replies")
            .document(reply.replyId)
            .set(reply)
            .await()
    }
    override fun getReplies(
        postId: String,
        commentId: String
    ): Flow<List<CommentReply>> = callbackFlow {

        val listener = firestore.collection("posts")
            .document(postId)
            .collection("comments")
            .document(commentId)
            .collection("replies")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val replies = snapshot?.documents?.mapNotNull {
                    it.toObject(CommentReply::class.java)
                } ?: emptyList()

                trySend(replies)
            }

        awaitClose {
            listener.remove()
        }
    }
}