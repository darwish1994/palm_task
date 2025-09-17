package com.theminesec.example.question1.feature.channel.domain.repo

import com.theminesec.example.question1.feature.channel.domain.model.Channel

interface ChannelsRepository {
    suspend fun getChannels(cursor: String? = null): Result<List<Channel>>
}