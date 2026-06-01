package com.example.socialconnect.Presentation.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Domain.UseCases.GetPostsUseCase
import com.example.socialconnect.Domain.UseCases.GetUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    init {
        getPosts()
        getCurrentUser()
    }

    private fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase().collect { posts ->
                _state.value = _state.value.copy(posts = posts)
            }
        }
    }
    private fun getCurrentUser() {
        viewModelScope.launch {

            val uid = FirebaseAuth
                .getInstance()
                .currentUser
                ?.uid ?: return@launch

            val user = getUserUseCase(uid)

            _state.value = _state.value.copy(
                profileImage = user.profileImage,
                currentUserId = uid
            )
        }
    }
    fun refreshUser() {
        getCurrentUser()
    }


    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onTabSelected(index: Int) {
        _state.value = _state.value.copy(selectedTab = index)
    }



}