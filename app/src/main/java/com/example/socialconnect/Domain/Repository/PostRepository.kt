package com.example.socialconnect.Domain.Repository


import com.example.socialconnect.Data.Model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun createPost(post: Post)
    fun getAllPosts(): kotlinx.coroutines.flow.Flow<List<Post>>
    fun getUserPosts(uid: String): Flow<List<Post>>
    suspend fun toggleLike(postId: String, userId: String)
    fun getFeedPosts(followingIds: List<String>): Flow<List<Post>>
    suspend fun getPostsByIds(ids: List<String>): List<Post>

}