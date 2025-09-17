package com.theminesec.example.question1.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theminesec.example.question1.feature.chat.data.local.MessageDao
import com.theminesec.example.question1.feature.chat.data.model.MessageDT

@Database(entities = [MessageDT::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}