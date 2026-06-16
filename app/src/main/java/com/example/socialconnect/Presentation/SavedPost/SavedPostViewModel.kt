package com.example.socialconnect.Presentation.SavedPost

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.PostUseCase.GetPostByIdsUseCase
import com.example.socialconnect.Domain.UseCases.PostUseCase.GetSavedPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject  constructor(
    private val getSavedPostsUseCase: GetSavedPostsUseCase,
    private val getPostByIdsUseCase: GetPostByIdsUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SavedState())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(isLoading = true)
        loadSavedPosts()
    }

    private fun loadSavedPosts() {
        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase()

            if (userId == null) {
                _state.value = _state.value.copy(isLoading = false)
                return@launch
            }

            getSavedPostsUseCase(userId)
                .collect { ids ->

                    Log.d("DEBUG_IDS", ids.toString())

                    val posts = if (ids.isNotEmpty()) {
                        getPostByIdsUseCase(ids)
                    } else {
                        emptyList()
                    }

                    _state.value = _state.value.copy(
                        savedPosts = posts,
                        isLoading = false
                    )
                }
        }
    }

}