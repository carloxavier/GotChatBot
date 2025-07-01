package com.sample.kmp

import androidx.compose.ui.window.ComposeUIViewController
import com.sample.kmp.ui.Chat

fun MainViewController(initialPrompt: String? = null) = ComposeUIViewController {
    Chat(initialPrompt)
}