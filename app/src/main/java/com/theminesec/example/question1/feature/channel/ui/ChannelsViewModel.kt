package com.theminesec.example.question1.feature.channel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theminesec.example.question1.feature.channel.domain.repo.ChannelsRepository
import com.theminesec.example.question1.feature.channel.ui.state.ChannelsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelsViewModel @Inject constructor(
    private val channelsRepository: ChannelsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ChannelsUiState>(ChannelsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadChannels() {
        viewModelScope.launch {
            _uiState.value = ChannelsUiState.Loading
            channelsRepository.getChannels().fold(
                onSuccess = { channels ->
                    _uiState.value = if (channels.isEmpty()) {
                        ChannelsUiState.Empty
                    } else {
                        ChannelsUiState.Success(channels)
                    }
                },
                onFailure = { error ->
                    _uiState.value = ChannelsUiState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }
}