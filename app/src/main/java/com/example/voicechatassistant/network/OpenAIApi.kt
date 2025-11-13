package com.example.voicechatassistant.network

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class Message(val role: String, val content: String)
data class ChatRequest(val model: String = "gpt-4o-mini", val messages: List<Message>)
data class ChatChoice(val message: Message)
data class ChatResponse(val choices: List<ChatChoice>)

interface OpenAIApi {
    @POST("chat/completions")
    @Headers("Content-Type: application/json")
    suspend fun chat(@Body body: ChatRequest): ChatResponse
}
