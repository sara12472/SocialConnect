package com.example.socialconnect.Domain.Repository


import com.example.socialconnect.Core.Resource

interface AuthRepository {

    suspend fun signup(
        name: String,
        username: String,
        email: String,
        password: String
    ): Resource<Boolean>
    suspend fun login(
        email: String,
        password: String
    ): Resource<Boolean>
    suspend fun resetPassword(
        email: String
    ): Resource<Boolean>
    suspend fun signInWithGoogle(idToken: String): Resource<Boolean>
   suspend fun getCurrentUserId(): String?

}