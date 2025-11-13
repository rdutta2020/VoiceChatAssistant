package com.example.voicechatassistant.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class Speaker(context: Context) {
    private val tts = TextToSpeech(context) {
        // default init
        it?.let { /* no-op */ }
    }

    init {
        tts.language = Locale.getDefault()
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_ID")
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}
