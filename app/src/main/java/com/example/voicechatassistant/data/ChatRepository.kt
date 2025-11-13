package com.example.voicechatassistant.data

import com.example.voicechatassistant.network.ChatRequest
import com.example.voicechatassistant.network.OpenAIApi
import com.example.voicechatassistant.network.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val api: OpenAIApi
) {
    suspend fun ask(question: String): String = withContext(Dispatchers.IO) {
        val req = ChatRequest(messages = listOf(Message("user", question)))
        val res = api.chat(req)
        res.choices.firstOrNull()?.message?.content ?: "Sorry, no response."
    }
}
