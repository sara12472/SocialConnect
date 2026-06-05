package com.example.socialconnect.Data.Repository

import com.example.socialconnect.Data.Model.Post
import com.example.socialconnect.Domain.Repository.SavedPostRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class SavedPostRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : SavedPostRepository {
    override suspend fun savePost(postId: String) {

        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(userId)
            .collection("savedPosts")
            .document(postId)
            .set(
                mapOf(
                    "postId" to postId,
                    "savedAt" to System.currentTimeMillis()
                )
            )
            .await()
    }
    override suspend fun unSavePost(postId: String) {

        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(userId)
            .collection("savedPosts")
            .document(postId)
            .delete()
            .await()
    }
    override fun getSavedPostIds(userId: String): Flow<List<String>> = callbackFlow {

        val listener = firestore.collection("users")
            .document(userId)
            .collection("savedPosts")
            .addSnapshotListener { snapshot, error ->

                if (error != null || snapshot == null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val ids = snapshot.documents.map { it.id }
                trySend(ids)
            }

        awaitClose { listener.remove() }
    }

    override suspend fun getSavedPosts(): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun isPostSaved(postId: String): Boolean {
        TODO("Not yet implemented")
    }




}