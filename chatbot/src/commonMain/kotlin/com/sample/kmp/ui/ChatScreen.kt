package com.sample.kmp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.kmp.di.DI

@Composable
fun ChatScreen(
    initialPrompt: String? = null,
    viewModel: ChatViewModel = viewModel { ChatViewModel(DI.provideChatRepository()) }
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val userInput by viewModel.userInput.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            MessageInput(
                value = userInput,
                onValueChange = viewModel::onUserInput,
                onSendClick = { viewModel.sendMessageToModel(userInput) },
                enabled = !isLoading
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MessageList(
                messages = messages,
                modifier = Modifier.weight(1f)
            )
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(8.dp))
            }
        }
    }
    LaunchedEffect(initialPrompt) {
        initialPrompt?.let {
            viewModel.sendMessageToModel(initialPrompt)
        }
    }
}
