package com.theminesec.example.question1.feature.chat.domain.repo

import com.theminesec.example.question1.feature.chat.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(): Flow<List<Message>>
    suspend fun sendMessage(message: Message)
}