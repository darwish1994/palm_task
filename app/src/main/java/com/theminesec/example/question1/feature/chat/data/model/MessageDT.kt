package com.theminesec.example.question1.feature.chat.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.theminesec.example.question1.feature.chat.domain.model.Message

@Entity
data class MessageDT(
    @PrimaryKey
    val id: String,
    val message: String,
    val sender: String,
)


fun MessageDT.toDomainModel(): Message = Message(
    id = id,
    message = message,
    sender = sender,
)
fun List<MessageDT>.toListDomainModel(): List<Message> = map { it.toDomainModel() }
