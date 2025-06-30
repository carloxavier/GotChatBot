package com.sample.kmp.di

import com.sample.kmp.data.LLMDataSource
import com.sample.kmp.data.LLMRepositoryImpl

expect fun getChatDataSource(): LLMDataSource

object DI {
    fun provideChatRepository() = LLMRepositoryImpl(getChatDataSource())
}
