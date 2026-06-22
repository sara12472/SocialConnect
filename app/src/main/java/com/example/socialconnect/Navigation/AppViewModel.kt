package com.example.socialconnect.Navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _currentUserId = MutableStateFlow("")
    val currentUserId = _currentUserId.asStateFlow()

    private val _profileImage = MutableStateFlow("")
    val profileImage = _profileImage.asStateFlow()

    init {
        loadCurrentUser()
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            val uid = getCurrentUserIdUseCase() ?: return@launch
            Log.d("AppViewModel", "Loaded UID: $uid, instance hash: ${hashCode()}")
            _currentUserId.value = uid

            val user = getUserUseCase(uid)
            Log.d("AppViewModel", "Loaded user: ${user.username}")
            _profileImage.value = user.profileImage
        }
    }

    fun clearUser() {
        _currentUserId.value = ""
        _profileImage.value = ""
    }
}