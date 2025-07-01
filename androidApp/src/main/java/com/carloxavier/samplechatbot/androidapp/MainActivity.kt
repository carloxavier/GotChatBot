package com.carloxavier.samplechatbot.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sample.kmp.ui.Chat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Chat(
                initialPrompt = "You are a yoga coach, " +
                        "help me find a routine for yoga that fits me, " +
                        "start with a brief intro of yourself, max 120 chars, and ask about what I can do for you."
            )
        }
    }
}
