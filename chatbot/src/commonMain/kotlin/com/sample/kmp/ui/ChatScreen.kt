package com.sample.kmp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.kmp.di.DI

@Composable
fun ChatScreen(
    initialPrompt: String? = null,
    viewModel: ChatViewModel = viewModel { ChatViewModel(DI.provideChatRepository()) }
) {
    val state by viewModel.chatState.collectAsStateWithLifecycle()
    ChatScreenContent(
        state,
        onSendClick = { viewModel.sendMessageToModel(state.userInput) },
        onUserInput = viewModel::onUserInput
    )
    LaunchedEffect(initialPrompt) {
        initialPrompt?.let {
            viewModel.sendMessageToModel(initialPrompt)
        }
    }
}
