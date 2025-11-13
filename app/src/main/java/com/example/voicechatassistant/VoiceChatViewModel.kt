package com.example.voicechatassistant

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.voicechatassistant.data.ChatRepository
import com.example.voicechatassistant.voice.Speaker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoiceChatViewModel @Inject constructor(
    application: Application,
    private val repo: ChatRepository
) : AndroidViewModel(application) {

    private val _conversation = MutableStateFlow<List<Pair<Boolean, String>>>(emptyList())
    val conversation: StateFlow<List<Pair<Boolean, String>>> = _conversation

    private val speaker = Speaker(application)

    fun addUserMessage(text: String) {
        _conversation.value = _conversation.value + (true to text)
        viewModelScope.launch {
            val reply = repo.ask(text)
            _conversation.value = _conversation.value + (false to reply)
            speaker.speak(reply)
        }
    }

    override fun onCleared() {
        super.onCleared()
        speaker.shutdown()
    }
}
