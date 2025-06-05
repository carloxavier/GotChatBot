package com.sample.kmp.domain

sealed class Message(
    open val text: String,
) {
    val isFromUser: Boolean
        get() = this is UserMessage

    data class UserMessage(override val text: String) : Message(text)
    data class ModelMessage(override val text: String) : Message(text)


    companion object {
        fun user(text: String?) = UserMessage(text = text ?: "")
        fun model(text: String?) = ModelMessage(text = text ?: "")
    }
}