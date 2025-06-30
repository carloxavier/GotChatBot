package com.sample.kmp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sample.kmp.domain.ErrorMessage
import com.sample.kmp.domain.Message
import com.sample.kmp.domain.ModelMessage
import com.sample.kmp.domain.UserMessage

@Composable
fun MessageBubble(message: Message) = when (message) {
    is ModelMessage -> MessageContent(
        message = message.text,
        alignment = Alignment.CenterStart,
        bubbleColor = MaterialTheme.colorScheme.primaryContainer,
        textColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
    is UserMessage -> MessageContent(
        message = message.text,
        alignment = Alignment.CenterEnd,
        bubbleColor = MaterialTheme.colorScheme.secondaryContainer,
        textColor = MaterialTheme.colorScheme.onSecondaryContainer
    )
    is ErrorMessage -> MessageContent(
        message = message.text,
        alignment = Alignment.Center,
        bubbleColor = MaterialTheme.colorScheme.errorContainer,
        textColor = MaterialTheme.colorScheme.onErrorContainer
    )
}

@Composable
private fun MessageContent(
    message: String,
    alignment: Alignment,
    bubbleColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = bubbleColor),
            modifier = Modifier.widthIn(max = 300.dp) // Max width for bubbles
        ) {
            Text(
                text = message,
                color = textColor,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
