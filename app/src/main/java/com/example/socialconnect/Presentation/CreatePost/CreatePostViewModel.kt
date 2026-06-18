package com.example.socialconnect.Presentation.CreatePost

import com.example.socialconnect.Domain.UseCases.UploadMediaUseCase


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Data.Model.Post
import com.example.socialconnect.Domain.UseCases.PostUseCase.CreatePostUseCase
import com.example.socialconnect.Domain.UseCases.PostUseCase.GetPostUseCase
import com.example.socialconnect.Domain.UseCases.PostUseCase.UpdatePostUseCase
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(

    private val uploadMediaUseCase: UploadMediaUseCase,
    private val createPostUseCase: CreatePostUseCase,
    private val getUserUseCase: GetUserUseCase,

    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val updatePostUseCase: UpdatePostUseCase

) : ViewModel() {

    private val _state = MutableStateFlow(CreatePostState())
    val state = _state.asStateFlow()

    private var selectedUri: Uri? = null


    fun onCaptionChange(value: String) {

        _state.value = _state.value.copy(
            caption = value
        )
    }

    fun setSelectedMedia(uri: Uri, type: String) {


        if (_state.value.isEditMode) return

        selectedUri = uri

        _state.value = _state.value.copy(
            selectedMediaUri = uri.toString(),
            mediaType = type
        )
    }

    fun createPost(
        context: Context
    ) {

        val uri = selectedUri ?: return

        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true)

            try {

                val uid = getCurrentUserIdUseCase()
                if (uid == null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "User not found"
                    )
                    return@launch
                }

                val user = getUserUseCase(uid)

                val mediaUrl = uploadMediaUseCase(
                    uri,
                    context,
                    _state.value.mediaType
                )

                val post = Post(
                    postId = UUID.randomUUID().toString(),
                    userId = uid,
                    userName = user.username,
                    userProfile = user.profileImage,
                    caption = _state.value.caption,
                    mediaUrl = mediaUrl,
                    mediaType = _state.value.mediaType,
                    timestamp = System.currentTimeMillis()
                )

                createPostUseCase(post)

                _state.value = _state.value.copy(
                    isLoading = false,
                    isPosted = true
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Something went wrong"
                )
            }
        }
    }
    fun loadPost(postId: String) {
        Log.d("CREATE_POST", "loadPost called with = $postId")
        if (postId.isBlank()) return

        viewModelScope.launch {

            val post = getPostUseCase(postId) ?: return@launch

            Log.d("CREATE_POST", "POST LOADED: $post")

            _state.value = _state.value.copy(
                postId = post.postId,
                caption = post.caption,
                selectedMediaUri = post.mediaUrl,
                mediaType = post.mediaType,
                isEditMode = true
            )

            Log.d("CREATE_POST", "STATE AFTER LOAD: ${_state.value}")
        }
    }
    fun updatePost() {

        viewModelScope.launch {

            val postId = state.value.postId

            if (postId.isBlank()) return@launch

            _state.value = _state.value.copy(isLoading = true)

            updatePostUseCase(
                postId,
                state.value.caption
            )

            _state.value = _state.value.copy(
                isLoading = false,
                isPosted = true
            )
        }
    }
}