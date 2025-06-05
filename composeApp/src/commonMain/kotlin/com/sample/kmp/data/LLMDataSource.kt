package com.sample.kmp.data

import com.sample.kmp.domain.Message

interface LLMDataSource {
    suspend fun prompt(contentHistory: List<Message>): String?
}