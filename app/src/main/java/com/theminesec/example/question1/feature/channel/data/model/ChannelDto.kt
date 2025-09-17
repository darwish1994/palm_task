package com.theminesec.example.question1.feature.channel.data.model

data class ChannelDto(
    val id: String,
    val name: String,
    val unread: Int
)

fun ChannelDto.mapToEntity(): ChannelEntity = ChannelEntity(
    id = id,
    name = name,
    unread = unread,

    )
