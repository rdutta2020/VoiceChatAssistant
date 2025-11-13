package com.example.voicechatassistant.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class VoiceRecognizer(private val context: Context) {
    private val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val _results = Channel<String>(Channel.BUFFERED)
    val resultsFlow = _results.receiveAsFlow()

    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                Log.i("VoiceRecognizer", " Recognition error: $error")
            }
            override fun onResults(results: Bundle) {
                val text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.firstOrNull()
                Log.i("VoiceRecognizer", " onResults results: $results")
                if (text != null) _results.trySend(text)
            }
        })

        recognizer.startListening(intent)
    }

    fun stop() {
        recognizer.stopListening()
        recognizer.destroy()
    }
}
