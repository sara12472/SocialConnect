package com.example.socialconnect.Presentation.HomeScreen

import com.example.socialconnect.Data.Model.AppNotification
import com.example.socialconnect.Data.Model.Comment
import com.example.socialconnect.Data.Model.CommentReply
import com.example.socialconnect.Data.Model.Post
import com.example.socialconnect.Data.Model.Story
import kotlinx.coroutines.flow.MutableStateFlow

data class HomeState(

    val stories: List<Story> = emptyList(),
    val posts: List<Post> = emptyList(),

    val profileImage: String = "",

    val selectedTab: Int = 0,
    val currentUserId: String = "",

    val comments: List<Comment> = emptyList(),
    val commentText: String = "",
    val selectedPostId: String? = null,
    val showCommentsSheet: Boolean = false,

    val replyingToCommentId: String? = null,
    val replyingToUserName: String? = null,
    val repliesMap: Map<String, List<CommentReply>> = emptyMap(),

    val currentUserName: String = "",
    val currentUserProfile: String = "",
    val notifications: List<AppNotification> = emptyList(),
    val savedPostIds: List<String> = emptyList(),
    val savedPosts: List<Post> = emptyList(),

    val selectedPost: Post? = null
)