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
    val state by viewModel.chatState.collectAsStateWithLifecycle()
    Scaffold(
        bottomBar = {
            MessageInput(
                value = state.userInput,
                onValueChange = viewModel::onUserInput,
                onSendClick = { viewModel.sendMessageToModel(state.userInput) },
                enabled = !state.isLoading
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MessageList(
                messages = state.messages,
                modifier = Modifier.weight(1f)
            )
            if (state.isLoading) {
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
