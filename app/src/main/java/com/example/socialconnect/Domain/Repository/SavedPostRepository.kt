package com.example.socialconnect.Domain.Repository

import com.example.socialconnect.Data.Model.Post
import kotlinx.coroutines.flow.Flow

interface SavedPostRepository {
    suspend fun savePost(postId: String)

    suspend fun unSavePost(postId: String)

    suspend fun getSavedPosts(): List<Post>

    suspend fun isPostSaved(postId: String): Boolean
    fun getSavedPostIds(userId: String): Flow<List<String>>}
