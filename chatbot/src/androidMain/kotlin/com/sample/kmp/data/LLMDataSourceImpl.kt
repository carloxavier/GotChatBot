package com.sample.kmp.data

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Content
import com.google.firebase.ai.type.GenerativeBackend
import com.sample.kmp.domain.Message
import com.sample.kmp.domain.UserMessage

class LLMDataSourceImpl : LLMDataSource {
    override suspend fun prompt(contentHistory: List<Message>): String? {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.0-flash")
        val content = contentHistory.map { it.toContent() }.toTypedArray()
        val response = model.generateContent(*content)
        return response.text
    }
}

private fun Message.senderType() = if (this is UserMessage) "user" else "model"

private fun Message.toContent() =
    Content.Builder()
        .setRole(senderType())
        .text(text)
        .build()