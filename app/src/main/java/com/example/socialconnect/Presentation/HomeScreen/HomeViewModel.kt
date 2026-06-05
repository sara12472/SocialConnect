package com.example.socialconnect.Presentation.HomeScreen

import android.util.Log
import androidx.compose.remote.creation.dsl.first
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Data.Model.AppNotification
import com.example.socialconnect.Data.Model.Comment
import com.example.socialconnect.Data.Model.CommentReply
import com.example.socialconnect.Data.Model.Post
import com.example.socialconnect.Data.Service.AppNotificationManager
import com.example.socialconnect.Domain.Repository.SavedPostRepository
import com.example.socialconnect.Domain.UseCases.AddCommentUseCase
import com.example.socialconnect.Domain.UseCases.AddReplyUseCase
import com.example.socialconnect.Domain.UseCases.GetCommentsUseCase
import com.example.socialconnect.Domain.UseCases.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.GetFcmTokenUseCase
import com.example.socialconnect.Domain.UseCases.GetFeedPostsUseCase
import com.example.socialconnect.Domain.UseCases.GetFollowingUsersUseCase
import com.example.socialconnect.Domain.UseCases.GetNotificationsUseCase
import com.example.socialconnect.Domain.UseCases.GetPostByIdsUseCase
import com.example.socialconnect.Domain.UseCases.GetPostsUseCase
import com.example.socialconnect.Domain.UseCases.GetRepliesUseCase
import com.example.socialconnect.Domain.UseCases.GetSavedPostsUseCase
import com.example.socialconnect.Domain.UseCases.GetUserUseCase
import com.example.socialconnect.Domain.UseCases.SendNotificationUseCase
import com.example.socialconnect.Domain.UseCases.ToggleLikeUseCase
import com.example.socialconnect.Domain.UseCases.UnSavePostUseCase
import com.example.socialconnect.Domain.UseCases.UpdateFcmTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private  val getCommentUseCase: GetCommentsUseCase,
    private  val addCommentUseCase: AddCommentUseCase,
    private val addReplyUseCase: AddReplyUseCase,
    private val getReplyUseCase: GetRepliesUseCase,
    private val getFcmTokenUseCase: GetFcmTokenUseCase,
    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase,
    private  val getNotificationsUseCase: GetNotificationsUseCase,
    private  val savedPostsUseCase: GetSavedPostsUseCase,
    private val unSavePostUseCase: UnSavePostUseCase,
    private  val getSavedPostsUseCase: GetSavedPostsUseCase,





    ) : ViewModel() {

    init {
      getPosts()
        getCurrentUser()
        saveFcmToken()
        loadNotifications()
        loadSavedPosts()

       // getFeedPosts()


    }

   private fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase().collect { posts ->
                _state.value = _state.value.copy(posts = posts)
            }
        }
    }
   /* private fun getFeedPosts() {

        viewModelScope.launch {

            val currentUserId =
                getCurrentUserIdUseCase() ?: return@launch

            getFollowingUserUseCase(currentUserId)
                .collect { followingIds ->

                    getFeedPostUseCase(followingIds)
                        .collect { posts ->

                            _state.value = _state.value.copy(
                                posts = posts
                            )
                        }
                }
        }
    }*/
    private fun getCurrentUser() {
        viewModelScope.launch {

            val uid = getCurrentUserIdUseCase()
                ?: return@launch

            val user = getUserUseCase(uid)

            _state.value = _state.value.copy(
                profileImage = user.profileImage,
                currentUserId = uid
            )
        }
    }

    fun refreshUser() {
        getCurrentUser()
    }


    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onTabSelected(index: Int) {
        _state.value = _state.value.copy(selectedTab = index)
    }

    fun onLikeClick(post: Post) {

        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch

            val receiver = getUserUseCase(post.userId)

            // ❗ 1. OWN POST CHECK FIRST
            if (post.userId == userId) {
                toggleLikeUseCase(post.postId, userId)
                return@launch
            }

            // ❗ 2. TOGGLE LIKE ALWAYS (even if notifications OFF)
            toggleLikeUseCase(post.postId, userId)

            // ❗ 3. NOTIFICATION CHECK
            if (!receiver.notificationsEnabled) return@launch

            val user = getUserUseCase(userId)

            val notification = AppNotification(
                senderId = userId,
                senderName = user.name,
                senderProfile = user.profileImage,
                receiverId = post.userId,
                postId = post.postId,
                type = "like"
            )

            sendNotificationUseCase(notification)

        }
    }

    fun onCommentTextChange(text: String) {
        _state.value = _state.value.copy(commentText = text)
    }

    fun openComments(postId: String) {
        _state.value = _state.value.copy(
            selectedPostId = postId,
            showCommentsSheet = true
        )

        loadComments(postId)
        loadReplies(postId)
    }

    fun closeComments() {
        _state.value = _state.value.copy(
            showCommentsSheet = false,
            comments = emptyList(),
            commentText = ""
        )
    }

    private fun loadComments(postId: String) {

        viewModelScope.launch {

            getCommentUseCase(postId).collect { comments ->

                _state.value = _state.value.copy(comments = comments)

                // replies ko ALAG collect karo (important fix)
                comments.forEach { comment ->

                    viewModelScope.launch {

                        getReplyUseCase(postId, comment.commentId)
                            .collect { replies ->

                                val updatedMap = _state.value.repliesMap.toMutableMap()
                                updatedMap[comment.commentId] = replies

                                _state.value = _state.value.copy(
                                    repliesMap = updatedMap
                                )
                            }
                    }
                }
            }
        }
    }

    fun sendComment() {

        viewModelScope.launch @androidx.annotation.RequiresPermission(android.Manifest.permission.POST_NOTIFICATIONS) {

            val state = _state.value
            val userId = getCurrentUserIdUseCase() ?: return@launch
            val postId = state.selectedPostId ?: return@launch

            val user = getUserUseCase(userId)

            val isReply = state.replyingToCommentId != null

            // SAVE COMMENT / REPLY
            if (isReply) {

                addReplyUseCase(
                    CommentReply(
                        replyId = System.currentTimeMillis().toString(),
                        commentId = state.replyingToCommentId!!,
                        postId = postId,
                        userId = userId,
                        userName = user.name,
                        userProfile = user.profileImage,
                        text = state.commentText,
                        timestamp = System.currentTimeMillis()
                    )
                )

            } else {

                val comment = Comment(
                    commentId = System.currentTimeMillis().toString(),
                    postId = postId,
                    userId = userId,
                    userName = user.name,
                    userProfile = user.profileImage,
                    text = state.commentText,
                    timestamp = System.currentTimeMillis()
                )

                addCommentUseCase(comment)
            }

            // FIND POST OWNER
            val postOwnerId =
                state.posts.firstOrNull { it.postId == postId }?.userId


            if (postOwnerId == null || postOwnerId == userId) {
                _state.value = _state.value.copy(
                    commentText = "",
                    replyingToCommentId = null,
                    replyingToUserName = null
                )
                return@launch
            }


            val receiver = getUserUseCase(postOwnerId)

            if (!receiver.notificationsEnabled) {
                _state.value = _state.value.copy(
                    commentText = "",
                    replyingToCommentId = null,
                    replyingToUserName = null
                )
                return@launch
            }


            val notification = AppNotification(
                senderId = userId,
                senderName = user.name,
                senderProfile = user.profileImage,
                receiverId = postOwnerId,
                postId = postId,
                type = if (isReply) "reply" else "comment"
            )

            sendNotificationUseCase(notification)


            // RESET UI STATE
            _state.value = _state.value.copy(
                commentText = "",
                replyingToCommentId = null,
                replyingToUserName = null
            )
        }
    }

    fun startReply(comment: Comment) {

        _state.value = _state.value.copy(
            replyingToCommentId = comment.commentId,
            replyingToUserName = comment.userName,
            commentText = "@${comment.userName} "
        )
    }


    fun sendReply(text: String, comment: Comment) {


        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch
            val postId = state.value.selectedPostId ?: return@launch

            val user = getUserUseCase(userId)

            val reply = CommentReply(
                replyId = System.currentTimeMillis().toString(),
                commentId = comment.commentId,
                postId = postId,
                userId = userId,
                userName = user.name,
                userProfile = user.profileImage,
                text = text,
                timestamp = System.currentTimeMillis()
            )

            addReplyUseCase(reply)
        }
    }
    fun loadReplies(postId: String) {

        viewModelScope.launch {

            val comments = state.value.comments

            comments.forEach { comment ->

                getReplyUseCase(postId, comment.commentId)
                    .collect { replies ->

                        val updatedMap = state.value.repliesMap.toMutableMap()
                        updatedMap[comment.commentId] = replies

                        _state.value = _state.value.copy(
                            repliesMap = updatedMap
                        )
                    }
            }
        }
    }
    fun clearReply() {
        _state.value = _state.value.copy(
            replyingToCommentId = null,
            replyingToUserName = null
        )
    }
    private fun saveFcmToken() {
        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch

            val token = getFcmTokenUseCase()

            updateFcmTokenUseCase(userId, token)
        }
    }
    private fun loadNotifications() {
        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch

            getNotificationsUseCase(userId)
                .collect { notifications ->

                    _state.value = _state.value.copy(
                        notifications = notifications
                    )
                }
        }
    }
    fun onSaveClick(post: Post) {
        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch

            val isSaved = state.value.savedPostIds.contains(post.postId)

            if (isSaved) {
                unSavePostUseCase(post.postId)
            } else {
                savedPostsUseCase(post.postId)
            }
        }
    }

    private fun loadSavedPosts() {

        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch

            getSavedPostsUseCase(userId).collect { ids ->

                _state.value = _state.value.copy(
                    savedPostIds = ids
                )
            }
        }
    }


}







