package com.example.socialconnect.Domain.UseCases

import android.content.Context
import android.net.Uri
import com.example.socialconnect.Domain.Repository.AuthRepository
import com.example.socialconnect.Domain.Repository.EditProfileRepository
import jakarta.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: EditProfileRepository
) {

    suspend operator fun invoke(
        uri: Uri,
        context: Context
    ) = repository.uploadImageToCloudinary(uri, context)
}