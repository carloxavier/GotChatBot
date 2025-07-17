package com.sample.kmp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatScreenContent(
    state: ChatState, onSendClick: () -> Unit, onUserInput: (String) -> Unit
) {
    Scaffold(
        bottomBar = {
            MessageInput(
                value = state.userInput,
                onValueChange = onUserInput,
                onSendClick = onSendClick,
                enabled = !state.isLoading
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            MessageList(
                messages = state.messages, modifier = Modifier.weight(1f)
            )
            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenContentPreview() {
    val state = ChatState(
        messages = emptyList(),
        isLoading = false,
        userInput = ""
    )
    ChatScreenContent(state = state, onSendClick = {}, onUserInput = {})
}
