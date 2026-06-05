package com.example.socialconnect.Data.Repository

import com.example.socialconnect.Domain.Repository.FollowRepository
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

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
    override fun getFollowersCount(userId: String): Flow<Int> = callbackFlow {

        val listener = firestore.collection("followers")
            .document(userId)
            .collection("userFollowers")
            .addSnapshotListener { snapshot, _ ->
                trySend(snapshot?.size() ?: 0)
            }

        awaitClose { listener.remove() }
    }

    override fun getFollowingCount(userId: String): Flow<Int> = callbackFlow {

        val listener = firestore.collection("following")
            .document(userId)
            .collection("userFollowing")
            .addSnapshotListener { snapshot, _ ->
                trySend(snapshot?.size() ?: 0)
            }

        awaitClose { listener.remove() }
    }
    override fun getFollowingUsers(userId: String): Flow<List<String>> =
        callbackFlow {

            val listener = firestore.collection("following")
                .document(userId)
                .collection("userFollowing")
                .addSnapshotListener { snapshot, _ ->

                    val ids = snapshot?.documents?.map { it.id }
                        ?: emptyList()

                    trySend(ids)
                }

            awaitClose {
                listener.remove()
            }
        }
}