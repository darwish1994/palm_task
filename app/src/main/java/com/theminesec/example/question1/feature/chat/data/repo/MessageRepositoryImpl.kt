package com.theminesec.example.question1.feature.chat.data.repo

import com.theminesec.example.question1.feature.chat.data.local.MessageDao
import com.theminesec.example.question1.feature.chat.data.model.toListDomainModel
import com.theminesec.example.question1.feature.chat.data.remote.MessageApi
import com.theminesec.example.question1.feature.chat.domain.model.Message
import com.theminesec.example.question1.feature.chat.domain.repo.MessageRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val localDataSource: MessageDao,
    private val remoteDataSource: MessageApi
) : MessageRepository {
    override fun getMessages() = localDataSource.getAllMessages().map {it.toListDomainModel()  }
    override suspend fun sendMessage(message: Message) {
        remoteDataSource.send(message)
    }
}