package com.example.socialconnect.Domain.Repository

interface FollowRepository {
    suspend fun followUser(currentUserId: String, targetUserId: String)
    suspend fun unfollowUser(currentUserId: String, targetUserId: String)
    suspend fun isFollowing(currentUserId: String, targetUserId: String): Boolean
}