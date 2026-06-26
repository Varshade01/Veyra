# Offline Guard: Privacy-First Audio Protocol App

An advanced, zero-internet Android application designed for high-security environments. It records live conversations, transcribes audio to text locally, and generates structured summaries using on-device Large Language Models (LLMs).

---

# 🏛 System Architecture & Data Flow

The application follows Clean Architecture principles combined with MVVM and unidirectional data flow. All ML processing happens strictly on the device hardware via specialized runtimes.

```text
┌───────────────────────┐
│      Microphone       │
└──────────┬────────────┘
           │
           │ Raw PCM (16kHz, Mono)
           ▼
┌───────────────────────┐
│   Android AudioRecord │
└──────────┬────────────┘
           │
           │ Kotlin Coroutines (IO)
           ▼
┌─────────────────────────────────────┐
│  Vosk Acoustic Model / Whisper.cpp  │
└──────────┬──────────────────────────┘
           │
           │ Dynamic String Tokens
           ▼
┌───────────────────────┐
│      Kotlin Flow      │────────────► Jetpack Compose UI
└──────────┬────────────┘
           │
           │ Full Transcript
           ▼
┌───────────────────────┐
│ Prompt Engineering    │
│ System Context        │
└──────────┬────────────┘
           │
           ▼
┌───────────────────────────────────────────┐
│ Google AI Edge SDK (Gemma-2B INT8 Model)  │
└──────────┬────────────────────────────────┘
           │
           │ Android NNAPI / NPU
           ▼
┌───────────────────────┐
│ Structured Summary    │
└──────────┬────────────┘
           │
           ▼
      UI Presentation
```

---

# 🛠 Technology Stack

| Layer | Technology |
|--------|------------|
| **UI Framework** | Jetpack Compose (Single Activity) |
| **Architecture** | Clean Architecture + MVVM + Repository Pattern |
| **Concurrency** | Kotlin Coroutines, StateFlow, SharedFlow |
| **Dependency Injection** | Hilt |
| **Audio Engine** | Android AudioRecord API (PCM 16-bit, 16kHz Mono) |
| **Speech-to-Text** | Vosk Android SDK / Whisper.cpp |
| **LLM Engine** | Google AI Edge SDK (`com.google.mediapipe:tasks-genai`) |
| **ML Pipeline** | Python 3.10+, PyTorch, Hugging Face, ONNX, MediaPipe Quantization |

---

# 🎯 Implementation Roadmap

## Milestone 1 — Python Pipeline & Model Preparation

- Initialize a Python 3.10+ virtual environment (`venv`)
- Install:
  - transformers
  - torch
  - onnx
  - mediapipe
- Download **Gemma-2B-it** from Hugging Face.
- Quantize FP16 weights → **INT8** to reduce memory usage (<1.5GB).
- Download lightweight offline acoustic models from Vosk (~50MB).

---

## Milestone 2 — Android Core Infrastructure & UI

### Project Setup

- Java 17
- Kotlin
- Hilt
- Jetpack Compose
- Lifecycle libraries

### Audio Engine

- Runtime permission handling (`RECORD_AUDIO`)
- AudioRecord implementation
- Coroutine-based recording service

### UI

**Record Screen**

- Live waveform
- Record / Stop actions
- Timer

**Result Screen**

- Transcript panel
- AI Summary card

---

## Milestone 3 — Offline Speech-to-Text

### SDK Integration

- Integrate Vosk Android SDK
- Extract model assets into internal storage

### Real-Time Pipeline

```
AudioRecord
      │
      ▼
Dispatchers.IO
      │
      ▼
Vosk Recognizer
      │
      ▼
Repository
      │
      ▼
Flow<String>
      │
      ▼
Compose UI
```

### UI Binding

Expose recognition output through:

```kotlin
Flow<String>
```

to continuously update the transcript.

---

## Milestone 4 — On-Device LLM Summarization

### Inference Engine

Integrate

```text
com.google.mediapipe:tasks-genai
```

Load the quantized Gemma model from device storage.

### Prompt Engineering

```text
You are a precise assistant.

Summarize the following transcript into
3 bullet points of key decisions.

Do not add outside information.

Transcript:
[User Text]

Summary:
```

### Background Execution

Run inference using

```kotlin
Dispatchers.Default
```

and

```kotlin
generateResultAsync()
```

to avoid UI blocking and ANRs.

---

## Milestone 5 — Optimization & Memory Management

### Memory Optimization

- Android Studio Profiler
- Heap allocation analysis
- Explicit lifecycle cleanup

```kotlin
override fun onCleared() {
    llmInference.close()
}
```

### Edge Cases

- Low storage detection
- Microphone disconnection recovery
- Graceful model loading failures

---

# 📈 Key Engineering Highlights

## 1. Edge AI Expertise

Runs modern Large Language Models entirely on-device without cloud inference, minimizing latency and preserving user privacy.

---

## 2. Advanced Memory Management

Efficiently manages 1GB+ quantized LLMs while avoiding OutOfMemory exceptions through proper lifecycle management and model unloading.

---

## 3. Low-Level Android Engineering

Works directly with:

- PCM audio streams
- AudioRecord API
- Android NNAPI
- Neural Processing Units (NPU)

---

## 4. Cross-Disciplinary Engineering

Includes a production-ready Python machine learning asset pipeline inside:

```text
/ml-pipeline
```

demonstrating expertise across Android, AI deployment, and model optimization.

---

# 🚀 Project Goals

- 100% Offline Operation
- Privacy by Design
- Zero Cloud Dependencies
- Modern Android Architecture
- On-Device Speech Recognition
- On-Device LLM Summarization
- Production-Level Memory Management
- Recruiter-Ready Portfolio Project
