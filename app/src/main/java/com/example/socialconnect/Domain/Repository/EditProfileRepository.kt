package com.example.socialconnect.Domain.Repository

import android.content.Context
import android.net.Uri
import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Data.Model.User

interface EditProfileRepository {
    suspend fun uploadImageToCloudinary(
        uri: Uri,
        context: Context
    ): String
    suspend fun updateUser(user: User): Resource<Boolean>
    suspend fun getUser(uid: String): User

}