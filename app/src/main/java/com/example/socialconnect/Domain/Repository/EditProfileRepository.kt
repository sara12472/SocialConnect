package com.example.socialconnect.Domain.Repository

import android.content.Context
import android.net.Uri
import com.example.socialconnect.Core.Resource
import com.example.socialconnect.Data.Model.User

interface EditProfileRepository {
    suspend fun uploadMediaToCloudinary(
        uri: Uri,
        context: Context,
        type: String
    ): String
    suspend fun updateUser(user: User): Resource<Boolean>
    suspend fun getUser(uid: String): User
    suspend fun updateFcmToken(userId: String, token: String)

}