package com.example.socialconnect.Presentation.CreatePost


data class CreatePostState(

    val caption: String = "",

    val selectedMediaUri: String = "",

    val mediaType: String = "",

    val isLoading: Boolean = false,

    val isPosted: Boolean = false,

    val error: String = "",
    val postId: String = "",
    val isEditMode: Boolean = false

)