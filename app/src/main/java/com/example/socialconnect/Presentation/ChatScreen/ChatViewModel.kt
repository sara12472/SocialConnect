package com.example.socialconnect.Presentation.ChatScreen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Domain.UseCases.ChatUseCase.DeleteChatForUserUseCase
import com.example.socialconnect.Domain.UseCases.ChatUseCase.GetChatsUseCase
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val deleteChatForUserUseCase: DeleteChatForUserUseCase
) : ViewModel() {

    var state by mutableStateOf(ChatState())
        private set

    init {
        loadChats()
    }

    fun loadChats() {

        viewModelScope.launch {

            try {

                val currentUserId =
                    getCurrentUserIdUseCase() ?: run {
                        state = state.copy(isLoading = false)
                        return@launch
                    }

                state = state.copy(currentUserId = currentUserId, isLoading = true)

                val chats = getChatsUseCase(currentUserId)

                val updatedChats = chats
                    .filter { it.lastMessage.isNotBlank() }
                    .map { chat ->

                        val otherUserId =
                            chat.participants.firstOrNull { it != currentUserId } ?: ""

                        val user = getUserUseCase(otherUserId)

                        chat.copy(
                            otherUserId = otherUserId,
                            otherUserName = user.username,
                            otherUserProfile = user.profileImage
                        )
                    }

                val currentUser = getUserUseCase(currentUserId)

                state = state.copy(
                    chats = updatedChats,
                    currentUserName = currentUser.username,
                    isLoading = false
                )

            } catch (e: Exception) {

                state = state.copy(
                    error = e.message ?: "Unknown Error",
                    isLoading = false
                )
            }
        }
    }
    fun deleteChat(chatId: String) {
        if (chatId.isBlank()) return

        viewModelScope.launch {
            deleteChatForUserUseCase(
                chatId = chatId,
                userId = state.currentUserId
            )
            loadChats()
        }
    }
}