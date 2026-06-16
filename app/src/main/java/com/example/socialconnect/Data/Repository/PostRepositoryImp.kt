package com.example.socialconnect.Data.Repository


import com.example.socialconnect.Data.Model.Post
import com.example.socialconnect.Domain.Repository.PostRepository
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : PostRepository {

    override suspend fun createPost(
        post: Post
    ) {

        firestore
            .collection("posts")
            .document(post.postId)
            .set(post)
            .await()
    }

    override fun getAllPosts(): Flow<List<Post>> = callbackFlow {

        val listener = firestore.collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null || snapshot == null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val posts = snapshot?.documents
                    ?.mapNotNull { it.toObject(Post::class.java) }
                    ?: return@addSnapshotListener

                trySend(posts)
            }

        awaitClose {
            listener.remove()
        }
    }
    override fun getUserPosts(uid: String): Flow<List<Post>> = callbackFlow {

        val listener = firestore.collection("posts")
            .whereEqualTo("userId", uid)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null || snapshot == null) {
                    return@addSnapshotListener
                }

                val posts = snapshot.documents.mapNotNull {
                    it.toObject(Post::class.java)
                }

                trySend(posts)
            }

        awaitClose {
            listener.remove()
        }
    }
    override suspend fun toggleLike(postId: String, userId: String) {

        val postRef = firestore.collection("posts").document(postId)

        firestore.runTransaction { transaction ->

            val snapshot = transaction.get(postRef)

            val post = snapshot.toObject(Post::class.java)

            val likedBy = post?.likedBy?.toMutableList() ?: mutableListOf()

            val isLiked = likedBy.contains(userId)

            if (isLiked) {
                likedBy.remove(userId)
            } else {
                likedBy.add(userId)
            }

            transaction.update(postRef, mapOf(
                "likedBy" to likedBy,
                "likes" to likedBy.size
            ))
        }.await()
    }
    override fun getFeedPosts(
        followingIds: List<String>
    ): Flow<List<Post>> = callbackFlow {

        if (followingIds.isEmpty()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener = firestore.collection("posts")
            .whereIn("userId", followingIds)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null || snapshot == null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val posts = snapshot.documents.mapNotNull {
                    it.toObject(Post::class.java)
                }

                trySend(posts)
            }

        awaitClose {
            listener.remove()
        }
    }
    override suspend fun getPostsByIds(ids: List<String>): List<Post> {

        if (ids.isEmpty()) return emptyList()

        val chunks = ids.chunked(10)
        val result = mutableListOf<Post>()

        for (chunk in chunks) {

            val snapshot = firestore.collection("posts")
                .whereIn(FieldPath.documentId(), chunk)
                .get()
                .await()

            result.addAll(snapshot.toObjects(Post::class.java))
        }

        return result
    }
}