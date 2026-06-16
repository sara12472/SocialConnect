package com.example.socialconnect.Domain.Repository

import com.example.socialconnect.Data.Model.Post
import kotlinx.coroutines.flow.Flow

interface SavedPostRepository {

    suspend fun savePost(postId: String)

    suspend fun unSavePost(postId: String)

    fun getSavedPostIds(userId: String): Flow<List<String>>

    suspend fun isPostSaved(postId: String): Boolean
}
