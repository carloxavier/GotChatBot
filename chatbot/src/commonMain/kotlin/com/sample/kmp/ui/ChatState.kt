package com.sample.kmp.ui

import com.sample.kmp.domain.Message

data class ChatState(
    val messages: List<Message>,
    val isLoading: Boolean,
    val userInput: String
)