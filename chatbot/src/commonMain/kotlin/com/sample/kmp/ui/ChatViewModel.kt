package com.sample.kmp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.kmp.domain.LLMRepository
import com.sample.kmp.domain.UserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(private val llmRepository: LLMRepository) : ViewModel() {

    private val _chatState = MutableStateFlow(
        ChatState(
            messages = listOf(),
            isLoading = false,
            userInput = ""
        )
    )
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()

    fun sendMessageToModel(userInput: String) {
        if (userInput.isBlank()) return
        _chatState.update { state ->
            state.copy(
                messages = state.messages + UserMessage(userInput),
                isLoading = true
            )
        }
        viewModelScope.launch {
            _chatState.update { state ->
                state.copy(
                    messages = state.messages + llmRepository.prompt(state.messages, userInput),
                    isLoading = false,
                    userInput = ""
                )
            }
        }
    }

    fun onUserInput(userInput: String) {
        _chatState.update { state ->
            state.copy(userInput = userInput)
        }
    }
}