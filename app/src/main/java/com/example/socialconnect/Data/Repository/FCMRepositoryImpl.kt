package com.example.socialconnect.Data.Repository

import com.example.socialconnect.Domain.Repository.FcmRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

class FcmRepositoryImpl @Inject constructor() : FcmRepository {

    override suspend fun getToken(): String {
        return suspendCancellableCoroutine { cont ->

            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(task.result, null)
                    } else {
                        cont.resumeWithException(task.exception ?: Exception("FCM error"))
                    }
                }
        }
    }
}