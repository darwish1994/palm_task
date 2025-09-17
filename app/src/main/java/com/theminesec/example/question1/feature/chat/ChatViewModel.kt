package com.theminesec.example.question1.feature.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theminesec.example.question1.feature.chat.domain.model.Message
import com.theminesec.example.question1.feature.chat.domain.repo.MessageRepository
import com.theminesec.example.question1.feature.chat.domain.usecase.GetMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: MessageRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _uiState


}