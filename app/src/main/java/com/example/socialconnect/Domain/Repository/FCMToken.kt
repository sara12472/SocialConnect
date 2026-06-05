package com.example.socialconnect.Domain.Repository
interface FcmRepository {
    suspend fun getToken(): String
}