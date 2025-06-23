package com.sample.kmp.data

import com.sample.kmp.domain.ErrorMessage
import com.sample.kmp.domain.LLMRepository
import com.sample.kmp.domain.Message
import com.sample.kmp.domain.ModelMessage
import com.sample.kmp.domain.UserMessage

class LLMRepositoryImpl(private val llmDataSource: LLMDataSource) : LLMRepository {
    override suspend fun prompt(messageHistory: List<Message>, prompt: String): Message {
        val contentHistory: List<Message> = messageHistory + UserMessage(prompt)
        return runCatching {
            llmDataSource.prompt(contentHistory)?.let {
                ModelMessage(it)
            } ?: throw Error("empty response")
        }.fold(
            onFailure = { error -> ErrorMessage(error.message!!) },
            onSuccess = { it }
        )
    }
}