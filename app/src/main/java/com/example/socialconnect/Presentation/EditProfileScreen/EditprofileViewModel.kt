package com.example.socialconnect.Presentation.EditProfileScreen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Data.Model.User
import com.example.socialconnect.Domain.UseCases.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.GetUserUseCase
import com.example.socialconnect.Domain.UseCases.UpdateUserUseCase
import com.example.socialconnect.Domain.UseCases.UploadMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadImageUseCase: UploadMediaUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()
    private val _isSaved = MutableStateFlow(false)
    val isSaved = _isSaved.asStateFlow()
    private var originalUser: User? = null

    fun onNameChange(value: String) {
        _state.value = _state.value.copy(name = value)
    }

    fun onUsernameChange(value: String) {
        _state.value = _state.value.copy(username = value)
    }

    fun onBioChange(value: String) {
        _state.value = _state.value.copy(bio = value)
    }


    fun pickImage() {
        _state.value = _state.value.copy(profileImage = "PICK_IMAGE")
    }

    fun onImagePicked(uri: String) {
        _state.value = _state.value.copy(selectedImageUri = uri)
    }


    fun uploadImage(
        uri: Uri,
        context: Context
    ) {

        viewModelScope.launch {

            val imageUrl =
                uploadImageUseCase(uri, context,"image")

            _state.value =
                _state.value.copy(
                    profileImage = imageUrl
                )
        }
    }
    fun saveProfile() {

        viewModelScope.launch {
            val uid = getCurrentUserIdUseCase() ?: return@launch

            val current = _state.value
            val old = originalUser

            val user = User(
                uid = uid,
                name = current.name.ifEmpty { old?.name ?: "" },
                username = current.username.ifEmpty { old?.username ?: "" },
                bio = current.bio.ifEmpty { old?.bio ?: "" },
                profileImage = current.profileImage.ifEmpty { old?.profileImage ?: "" }
            )

            updateUserUseCase(user)

            _isSaved.value = true
        }
    }
    fun loadUser() {

        viewModelScope.launch {

            val uid = getCurrentUserIdUseCase() ?: return@launch

            val user = getUserUseCase(uid)

            _state.value = _state.value.copy(
                name = user.name,
                username = user.username,
                bio = user.bio,
                profileImage = user.profileImage
            )
        }
    }
}