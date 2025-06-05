package com.sample.kmp.data

import com.sample.kmp.domain.LLMRepository
import com.sample.kmp.domain.Message

class LLMRepositoryImpl(private val llmDataSource: LLMDataSource) : LLMRepository {
    override suspend fun prompt(messageHistory: List<Message>, prompt: String): Result<Message> {
        val contentHistory: List<Message> = messageHistory + Message.user(prompt)
        return runCatching {
            val answer = llmDataSource.prompt(contentHistory)
            Message.model(answer)
        }
    }
}