package com.sample.kmp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun Chat(initialPrompt: String? = null) {
    MaterialTheme {
        ChatScreen(initialPrompt)
    }
}