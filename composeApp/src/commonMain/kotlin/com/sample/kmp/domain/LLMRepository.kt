package com.sample.kmp.domain

interface LLMRepository {
    suspend fun prompt(messageHistory: List<Message>, prompt: String): Message
}