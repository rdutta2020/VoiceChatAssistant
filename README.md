# Voice Chat Assistant (Android) - Scaffold
Final Flow
   User taps mic
      ↓
   SpeechRecognizer → "What's the weather today?"
      ↓
   Send to OpenAI
      ↓
   OpenAI → "It's sunny and 28°C today."
      ↓
   TTS speaks the answer
      ↓
   UI shows the full conversation

It fires POST "https://api.openai.com/v1/chat/completions" endpoint with user message and gets assistant reply.


## What is included
- Jetpack Compose UI
- SpeechRecognizer (Android STT)
- TextToSpeech (TTS)
- Retrofit + OkHttp integration to call OpenAI (chat completions)
- Hilt for DI
- MVVM (ViewModel + Repository)
- Basic permission handling & sample UI

## Setup steps
1. Open the project folder in Android Studio (Arctic Fox / Flamingo / Electric Eel or later recommended).
2. Replace the placeholder `YOUR_OPENAI_KEY` in `NetworkModule.kt` with a secure approach:
   - Use `local.properties` and `BuildConfig` or Android keystore / remote backend.
3. Sync Gradle and run on a device (emulator may not have microphone).
4. Grant microphone permission when prompted.
5. Tap **Start Talking** and speak. The app will send the transcribed text to OpenAI and speak the reply.

## Security & Production notes
- Do NOT hardcode API keys in source. Use a backend proxy or secure build config.
- Consider using rate limiting and caching for API calls.
- Add proper error handling for network / STT errors.
- For sensitive data, prefer on-device models (TFLite) or a vetted server-side approach.

## Extending the project
- Add conversation history persistence (Room).
- Use embeddings + vector DB for RAG (contextual memory).
- Replace SpeechRecognizer with Whisper (server-side) for higher accuracy.
- Add playback UI, message timestamps, and streaming responses.

## License
MIT
