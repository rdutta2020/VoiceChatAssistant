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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun VoiceChatScreen(viewModel: VoiceChatViewModel = viewModel()) {
    val conversation by viewModel.conversation.collectAsState()
    val ctx = LocalContext.current
    val recognizer = remember { VoiceRecognizer(ctx) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            recognizer.resultsFlow.collectLatest { text ->
                viewModel.addUserMessage(text)
            }
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(conversation) { pair ->
                val (isUser, msg) = pair
                Text(text = if (isUser) "You: $msg" else "AI: $msg", modifier = Modifier
                    .padding(8.dp))
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { recognizer.startListening() }, modifier = Modifier.padding(8.dp)) {
                Text("Start Talking")
            }
            Button(onClick = { recognizer.stop() }, modifier = Modifier.padding(8.dp)) {
                Text("Stop")
            }
        }
    }
}
