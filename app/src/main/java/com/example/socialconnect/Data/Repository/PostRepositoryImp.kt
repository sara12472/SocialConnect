package com.example.socialconnect.Data.Repository


import com.example.socialconnect.Data.Model.Post
import com.example.socialconnect.Domain.Repository.PostRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

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
}