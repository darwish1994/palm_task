package com.theminesec.example.question1.feature.channel.data.remote.response

import com.theminesec.example.question1.feature.channel.data.model.ChannelDto

data class ChannelsResponseDto(
    val data: List<ChannelDto>,
    val next_cursor: String?
)