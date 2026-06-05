package com.example.socialconnect.Data.Repository

import com.example.socialconnect.Data.Model.AppNotification
import com.example.socialconnect.Domain.Repository.NotificationRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : NotificationRepository {

    private val notificationRef = firestore.collection("notifications")
    override suspend fun sendNotification(notification: AppNotification) {

        val docRef = notificationRef
            .document(notification.receiverId)
            .collection("userNotifications")
            .document() // auto id

        val finalNotification = notification.copy(
            notificationId = docRef.id
        )

        docRef.set(finalNotification).await()
    }

    override fun getNotifications(userId: String): Flow<List<AppNotification>> = callbackFlow {

        val listener: ListenerRegistration =
            notificationRef
                .document(userId)
                .collection("userNotifications")
                .orderBy("timestamp")
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val notifications = snapshot?.documents?.mapNotNull {
                        it.toObject(AppNotification::class.java)
                    }?.reversed() ?: emptyList()

                    trySend(notifications)
                }

        awaitClose {
            listener.remove()
        }
    }
}