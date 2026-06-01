package com.example.socialconnect.Data.Repository

import com.example.socialconnect.Domain.Repository.FollowRepository
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class FollowRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FollowRepository {

    override suspend fun followUser(currentUserId: String, targetUserId: String) {

        firestore.collection("following")
            .document(currentUserId)
            .collection("userFollowing")
            .document(targetUserId)
            .set(mapOf("time" to System.currentTimeMillis()))

        firestore.collection("followers")
            .document(targetUserId)
            .collection("userFollowers")
            .document(currentUserId)
            .set(mapOf("time" to System.currentTimeMillis()))
    }

    override suspend fun unfollowUser(currentUserId: String, targetUserId: String) {

        firestore.collection("following")
            .document(currentUserId)
            .collection("userFollowing")
            .document(targetUserId)
            .delete()

        firestore.collection("followers")
            .document(targetUserId)
            .collection("userFollowers")
            .document(currentUserId)
            .delete()
    }

    override suspend fun isFollowing(currentUserId: String, targetUserId: String): Boolean {

        val doc = firestore.collection("following")
            .document(currentUserId)
            .collection("userFollowing")
            .document(targetUserId)
            .get()
            .await()

        return doc.exists()
    }
}