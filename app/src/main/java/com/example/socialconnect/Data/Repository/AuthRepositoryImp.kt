package com.example.socialconnect.Data.Repository


import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Data.Model.User
import com.example.socialconnect.Domain.Repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await


class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override suspend fun signup(
        name: String,
        username: String,
        email: String,
        password: String
    ): Resource<Boolean> {

        return try {

            val result = auth.createUserWithEmailAndPassword(email, password).await()

            val uid = result.user?.uid ?: ""

            val user = User(
                uid = uid,
                name = name,
                username = username,
                email = email
            )
            firestore.collection("users")
                .document(uid)
                .set(user)
                .await()

            Resource.Success(true)

        } catch (e: Exception) {

            Resource.Error(e.message ?: "Signup Failed")
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Resource<Boolean> {
        return try {

            auth.signInWithEmailAndPassword(
                email,
                password
            ).await()

            Resource.Success(true)

        } catch (e: Exception) {

            Resource.Error(
                e.message ?: "Login Failed"
            )
        }

    }
    override suspend fun resetPassword(
        email: String
    ): Resource<Boolean> {

        return try {

            auth.sendPasswordResetEmail(email).await()

            Resource.Success(true)

        } catch (e: Exception) {

            Resource.Error(
                e.message ?: "Failed to send reset email"
            )
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Resource<Boolean> {
        return try {

            val credential = GoogleAuthProvider.getCredential(idToken, null)

            val result = auth.signInWithCredential(credential).await()

            val user = result.user ?: return Resource.Error("User not found")

            val userData = User(
                uid = user.uid,
                name = user.displayName ?: "",
                username = user.email ?: "",
                email = user.email ?: ""
            )

            firestore.collection("users")
                .document(user.uid)
                .set(userData)
                .await()

            Resource.Success(true)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Google Sign-In Failed")
        }
    }
    override suspend fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}









