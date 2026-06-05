package com.example.socialconnect.Presentation.SavedPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Domain.UseCases.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.GetPostByIdsUseCase
import com.example.socialconnect.Domain.UseCases.GetSavedPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
        loadSavedPosts()
    }

    private fun loadSavedPosts() {
        viewModelScope.launch {

            val userId = getCurrentUserIdUseCase() ?: return@launch

            getSavedPostsUseCase(userId).collect { ids ->

                val posts = getPostByIdsUseCase(ids)

                _state.value = _state.value.copy(
                    savedPosts = posts
                )
            }
        }
    }

}