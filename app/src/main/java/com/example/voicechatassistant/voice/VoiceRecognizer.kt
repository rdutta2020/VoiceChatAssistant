package com.example.voicechatassistant.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class VoiceRecognizer(
    private val context: Context,
    private val onFinalResult: (String) -> Unit,
    private val onListeningStart: () -> Unit,
    private val onListeningStop: () -> Unit,
    private val onError: (String) -> Unit = {}
) {

    private val recognizer: SpeechRecognizer =
        SpeechRecognizer.createSpeechRecognizer(context)

    fun start() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        recognizer.setRecognitionListener(object : RecognitionListener {

            override fun onReadyForSpeech(params: Bundle?) {
                onListeningStart()
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                onListeningStop()
            }

            override fun onError(error: Int) {
                onListeningStop()
                onError("SpeechRecognizer error: $error")
            }

            override fun onResults(results: Bundle) {
                onListeningStop()
                val text = results.getStringArrayList(
                    SpeechRecognizer.RESULTS_RECOGNITION
                )?.firstOrNull()
                text?.let(onFinalResult)
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        recognizer.startListening(intent)
    }

    fun stop() {
        recognizer.stopListening()
        onListeningStop()
    }

    fun destroy() {
        recognizer.destroy()
    }
}
