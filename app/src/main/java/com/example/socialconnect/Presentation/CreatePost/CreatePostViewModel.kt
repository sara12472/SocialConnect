package com.example.socialconnect.Presentation.CreatePost

import com.example.socialconnect.Domain.UseCases.UploadMediaUseCase


import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Data.Model.Post
import com.example.socialconnect.Domain.UseCases.PostUseCase.CreatePostUseCase
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

    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase

) : ViewModel() {

    private val _state = MutableStateFlow(CreatePostState())
    val state = _state.asStateFlow()

    private var selectedUri: Uri? = null


    fun onCaptionChange(value: String) {

        _state.value = _state.value.copy(
            caption = value
        )
    }

    fun setSelectedMedia(
        uri: Uri,
        type: String
    ) {

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
}