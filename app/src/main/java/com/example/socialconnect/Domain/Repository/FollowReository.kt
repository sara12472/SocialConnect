package com.example.socialconnect.Domain.Repository

import kotlinx.coroutines.flow.Flow

interface FollowRepository {
    suspend fun followUser(currentUserId: String, targetUserId: String)
    suspend fun unfollowUser(currentUserId: String, targetUserId: String)
    suspend fun isFollowing(currentUserId: String, targetUserId: String): Boolean
    fun getFollowersCount(userId: String): Flow<Int>

    fun getFollowingCount(userId: String): Flow<Int>
    fun getFollowingUsers(userId: String): Flow<List<String>>
}