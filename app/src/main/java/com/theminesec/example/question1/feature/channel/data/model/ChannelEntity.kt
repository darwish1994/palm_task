package com.theminesec.example.question1.feature.channel.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.theminesec.example.question1.feature.channel.domain.model.Channel

@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey val id: String,
    val name: String,
    val unread: Int,
    val cachedAt: Long = System.currentTimeMillis()
)


fun ChannelEntity.mapToDomain(): Channel = Channel(
    id = id,
    name = name,
    unread = unread,
)
