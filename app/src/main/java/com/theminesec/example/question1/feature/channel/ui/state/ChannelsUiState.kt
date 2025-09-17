package com.theminesec.example.question1.feature.channel.ui.state

import com.theminesec.example.question1.feature.channel.domain.model.Channel

sealed class ChannelsUiState {
    object Loading : ChannelsUiState()
    object Empty : ChannelsUiState()
    data class Success(val channels: List<Channel>) : ChannelsUiState()
    data class Error(val message: String, val cachedData: List<Channel>? = null) : ChannelsUiState()
}