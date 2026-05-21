package com.example.socialconnect.Presentation.EditProfileScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class EditProfileViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    fun onNameChange(value: String) {
        _state.value = _state.value.copy(name = value)
    }

    fun onUsernameChange(value: String) {
        _state.value = _state.value.copy(username = value)
    }

    fun onBioChange(value: String) {
        _state.value = _state.value.copy(bio = value)
    }

    fun setImage(image: Int) {
        _state.value = _state.value.copy(profileImage = image)
    }
}