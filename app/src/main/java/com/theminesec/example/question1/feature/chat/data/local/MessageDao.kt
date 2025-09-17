package com.theminesec.example.question1.feature.chat.data.local

import androidx.room.Dao
import androidx.room.Query
import com.theminesec.example.question1.feature.chat.data.model.MessageDT
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messagedt")
    fun getAllMessages(): Flow<List<MessageDT>>

}