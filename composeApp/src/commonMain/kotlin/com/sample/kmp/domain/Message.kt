package com.sample.kmp.domain

sealed class Message(open val text: String)
data class UserMessage(override val text: String) : Message(text)
data class ModelMessage(override val text: String) : Message(text)
data class ErrorMessage(override val text: String) : Message(text)