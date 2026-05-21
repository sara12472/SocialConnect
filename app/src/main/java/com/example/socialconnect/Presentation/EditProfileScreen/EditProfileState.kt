package com.example.socialconnect.Presentation.EditProfileScreen

import com.example.socialconnect.R



data class EditProfileState(
    val name: String = "",
    val username: String = "",
    val bio: String = "",
    val profileImage: Int=R.drawable.profile
)
