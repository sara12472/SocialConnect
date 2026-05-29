package com.example.socialconnect.Presentation.ProfileScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Domain.UseCases.GetUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()
    init {
        loadUser()
    }


    fun onTabSelected(index: Int) {

        _state.value = _state.value.copy(
            selectedTab = index
        )
    }
    fun loadUser() {

        viewModelScope.launch {

            val uid =
                FirebaseAuth.getInstance()
                    .currentUser?.uid ?: return@launch

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