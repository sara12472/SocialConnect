package com.example.socialconnect.Presentation.EditProfileScreen

import com.example.socialconnect.R



data class EditProfileState(
    val name: String = "",
    val username: String = "",
    val bio: String = "",
    val profileImage: String = "",
    val selectedImageUri: String? = null

)
