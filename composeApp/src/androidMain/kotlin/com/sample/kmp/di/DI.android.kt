package com.sample.kmp.di

import com.sample.kmp.data.LLMDataSource
import com.sample.kmp.data.LLMDataSourceImpl

actual fun getChatDataSource(): LLMDataSource = LLMDataSourceImpl()