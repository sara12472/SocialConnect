package com.example.socialconnect.Data.Repository

import com.example.socialconnect.Data.Model.Chat
import com.example.socialconnect.Data.Model.Message
import com.example.socialconnect.Domain.Repository.MessageRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : MessageRepository {

    override suspend fun getChats(
        currentUserId: String
    ): List<Chat> {

        val snapshot = firestore
            .collection("chats")
            .whereArrayContains(
                "participants",
                currentUserId
            )
            .get()
            .await()

        return snapshot.toObjects(Chat::class.java)
    }
    override suspend fun getMessages(chatId: String): List<Message> {

        val snapshot = firestore
            .collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.DESCENDING)

            .get()
            .await()

        return snapshot.toObjects(Message::class.java)
    }

    override suspend fun sendMessage(
        chatId: String,
        message: Message
    ) {

        firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(message)
            .await()


        firestore.collection("chats")
            .document(chatId)
            .update(
                mapOf(
                    "lastMessage" to message.text,
                    "lastMessageTime" to message.timestamp
                )
            )
    }

    override suspend fun getOrCreateChat(
        currentUserId: String,
        otherUserId: String
    ): String {

        val query = firestore.collection("chats")
            .whereArrayContains("participants", currentUserId)
            .get()
            .await()

        val existingChat = query.documents.find {
            val participants = it.get("participants") as? List<*>
            participants?.contains(otherUserId) == true
        }

        if (existingChat != null) {
            return existingChat.id
        }

        val newChatRef = firestore.collection("chats").document()

        val chatData = mapOf(
            "participants" to listOf(currentUserId, otherUserId),
            "lastMessage" to "",
            "lastMessageTime" to 0L
        )

        newChatRef.set(chatData).await()

        return newChatRef.id
    }
    override suspend fun checkChatExists(currentUserId: String, otherUserId: String): String? {
        val snapshot = firestore.collection("chats")
            .whereArrayContains("participants", currentUserId)
            .get()
            .await()

        val chat = snapshot.documents.find {
            val participants = it.get("participants") as? List<*>
            participants?.contains(otherUserId) == true
        }

        return chat?.id
    }
    override suspend fun createChat(currentUserId: String, otherUserId: String): String {

        val newChatRef = firestore.collection("chats").document()

        val chatData = mapOf(
            "participants" to listOf(currentUserId, otherUserId),
            "lastMessage" to "",
            "lastMessageTime" to 0L
        )

        newChatRef.set(chatData).await()

        return newChatRef.id
    }
}