package com.sample.kmp.di

import com.sample.kmp.data.LLMDataSource

lateinit var llmDataSource: LLMDataSource
actual fun getChatDataSource(): LLMDataSource = llmDataSource