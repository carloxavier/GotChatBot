package com.sample.kmp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.kmp.domain.Message
import com.sample.kmp.domain.LLMRepository
import com.sample.kmp.domain.UserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val llmRepository: LLMRepository) : ViewModel() {
    private var _messages = MutableStateFlow(listOf<Message>())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()
    private var _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var _userInput = MutableStateFlow("")
    val userInput: StateFlow<String> = _userInput.asStateFlow()

    fun sendMessageToModel(userInput: String) {
        if (userInput.isBlank()) return
        _messages.value += UserMessage(text = userInput)
        _isLoading.value = true
        viewModelScope.launch {
            _messages.value += llmRepository.prompt(messages.value,userInput)
            _isLoading.value = false
            _userInput.value = ""
        }
    }

    fun onUserInput(userInput: String) {
        _userInput.value = userInput
    }
}