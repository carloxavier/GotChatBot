package com.sample.kmp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.kmp.domain.Message

@Composable
fun MessageList(messages: List<Message>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(8.dp),
        reverseLayout = true
    ) {
        items(messages.reversed()) {
            MessageBubble(it)
        }
    }
}
