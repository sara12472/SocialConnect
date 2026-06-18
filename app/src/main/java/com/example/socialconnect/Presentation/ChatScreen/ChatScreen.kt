package com.example.socialconnect.Presentation.ChatScreen


import CustomAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialconnect.Data.Model.Chat
import androidx.compose.material3.*
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState

import androidx.compose.ui.graphics.Color




@Composable
fun ChatScreen(
    navController: NavController,
    onChatClick: (String) -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val currentUserId = state.currentUserId

    var selectedChat by remember { mutableStateOf<Chat?>(null) }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()
        .imePadding()
    ) {

        CustomAppBar(
            title = state.currentUserName,
            onBackClick = { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Messages",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))


        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else if (state.chats.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No chats yet")
            }
        }


        else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(state.chats, key = { it.chatId + it.otherUserId }) { chat ->

                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { value ->
                            if (value == SwipeToDismissBoxValue.EndToStart) {
                                selectedChat = chat
                                showDeleteDialog = true
                            }
                            false
                        }
                    )
                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        enableDismissFromEndToStart = true,
                        backgroundContent = {
                            val color =
                                if (dismissState.targetValue == SwipeToDismissBoxValue.Settled)
                                    Color.Transparent
                                else
                                    Color.Red

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .background(color, RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    ) {
                        ChatItem(
                            chat = chat,
                            onClick = {
                                val otherUserId =
                                    chat.participants.firstOrNull { it != currentUserId }

                                if (!otherUserId.isNullOrBlank()) {
                                    onChatClick(otherUserId)
                                }
                            }
                        )
                    }

                }
            }
        }
    }
    if (showDeleteDialog && selectedChat != null) {

        val chatToDelete = selectedChat

        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = { Text("Delete Chat") },
            text = { Text("Delete this chat for yourself?") },

            confirmButton = {
                TextButton(onClick = {

                    showDeleteDialog = false
                    selectedChat = null

                    chatToDelete?.let {
                        if (it.chatId.isNotBlank()) {
                            viewModel.deleteChat(it.chatId)
                        }
                    }
                }) {
                    Text("Delete")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    selectedChat = null
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}



@Composable
fun ChatItem(
    chat: Chat,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = chat.otherUserProfile,
            contentDescription = null,
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop

        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = chat.otherUserName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = chat.lastMessage,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                maxLines = 1
            )
        }

        Text(
            text = chat.lastMessageTime.toTimeAgo(), // 👈 important
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}
fun Long.toTimeAgo(): String {
    val diff = System.currentTimeMillis() - this

    val minutes = diff / (1000 * 60)
    val hours = diff / (1000 * 60 * 60)

    return when {
        hours > 0 -> "${hours}h"
        minutes > 0 -> "${minutes}m"
        else -> "now"
    }
}