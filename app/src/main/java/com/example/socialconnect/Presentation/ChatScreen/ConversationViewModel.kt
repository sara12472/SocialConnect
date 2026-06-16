package com.example.socialconnect.Presentation.ChatScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Data.Model.Message
import com.example.socialconnect.Domain.UseCases.ChatUseCase.GetMessagesUseCase
import com.example.socialconnect.Domain.UseCases.ChatUseCase.GetOrCreateChatUseCase
import com.example.socialconnect.Domain.UseCases.ChatUseCase.SendMessageUseCase
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val getOrCreateChatUseCase: GetOrCreateChatUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    var state by mutableStateOf(ConversationState())
        private set

    fun initChat(otherUserId: String) {

        viewModelScope.launch {

            state = state.copy(isLoading = true)

            val currentUserId =
                getCurrentUserIdUseCase() ?: return@launch


            val chatId = getOrCreateChatUseCase(
                currentUserId,
                otherUserId
            )


            val messages = getMessagesUseCase(chatId)


            val otherUser = getUserUseCase(otherUserId)

            state = state.copy(
                chatId = chatId,
                otherUserId = otherUserId,
                currentUserId = currentUserId,
                messages = messages,
                otherUserName = otherUser.name,
                otherUsername = otherUser.username,
                otherUserProfile = otherUser.profileImage,
                isLoading = false
            )
        }
    }

    fun sendMessage(text: String) {

        viewModelScope.launch {

            val currentUserId =
                getCurrentUserIdUseCase() ?: return@launch

            val chatId = getOrCreateChatUseCase(
                currentUserId,
                state.otherUserId
            )

            val message = Message(
                senderId = currentUserId,
                text = text,
                timestamp = System.currentTimeMillis()
            )

            sendMessageUseCase(chatId, message)

            val updatedMessages =
                getMessagesUseCase(chatId)

            state = state.copy(
                chatId = chatId,
                messages = updatedMessages
            )
        }
    }
}