package com.example.voicechatassistant.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voicechatassistant.VoiceChatViewModel
import com.example.voicechatassistant.voice.VoiceRecognizer
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext

@Composable
fun VoiceChatScreen(viewModel: VoiceChatViewModel = viewModel()) {
    val ctx = LocalContext.current

    var isListening by remember { mutableStateOf(false) }

    val recognizer = remember {
        VoiceRecognizer(
            context = ctx,
            onFinalResult = { viewModel.addUserMessage(it) },
            onListeningStart = { isListening = true },
            onListeningStop = { isListening = false },
            onError = { isListening = false }
        )
    }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Conversation List
        val conversation by viewModel.conversation.collectAsState()

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(conversation) { (isUser, msg) ->
                Text(
                    text = if (isUser) "You: $msg" else "AI: $msg",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // 🔥 NEW: Listening indicator
        if (isListening) {
            Text(
                text = "Listening...",
                modifier = Modifier.padding(8.dp)
            )
        }

        Row {
            Button(
                onClick = { recognizer.start() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Start Talking")
            }

            Button(
                onClick = { recognizer.stop() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Stop")
            }
        }
    }
}
