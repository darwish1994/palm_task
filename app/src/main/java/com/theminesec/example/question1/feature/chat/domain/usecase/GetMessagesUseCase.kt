package com.theminesec.example.question1.feature.chat.domain.usecase

import com.theminesec.example.question1.feature.chat.domain.repo.MessageRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    operator fun invoke() = repository.getMessages()
}