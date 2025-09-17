package com.theminesec.example.question1.feature.chat.domain.usecase

import com.theminesec.example.question1.feature.chat.domain.model.Message
import com.theminesec.example.question1.feature.chat.domain.repo.MessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(message: Message) = repository.sendMessage(message)
}