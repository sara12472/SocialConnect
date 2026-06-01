package com.example.socialconnect.Domain.UseCases

import android.content.Context
import android.net.Uri
import com.example.socialconnect.Domain.Repository.EditProfileRepository
import jakarta.inject.Inject

class UploadMediaUseCase @Inject constructor(
    private val repository: EditProfileRepository
) {

    suspend operator fun invoke(
        uri: Uri,
        context: Context,
        type: String
    ) = repository.uploadMediaToCloudinary(uri, context, type)
}