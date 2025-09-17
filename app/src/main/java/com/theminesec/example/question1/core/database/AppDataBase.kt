package com.theminesec.example.question1.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theminesec.example.question1.feature.channel.data.local.ChannelDao
import com.theminesec.example.question1.feature.channel.data.model.ChannelEntity
import com.theminesec.example.question1.feature.chat.data.local.MessageDao
import com.theminesec.example.question1.feature.chat.data.model.MessageDT

@Database(entities = [MessageDT::class, ChannelEntity::class], version = 3)
abstract class AppDataBase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun channelDao(): ChannelDao

}