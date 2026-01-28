# Hau 🟢

**Hau** is a Kotlin Multiplatform UI toolkit for building elegant, responsive, and customizable chat applications.  
It demonstrates how to architect cross‑platform UIs using Compose Multiplatform, with clean MVVM patterns, mock data, and live previews.

---

## 📸 Preview

![Hau Screens](https://firebasestorage.googleapis.com/v0/b/tija-a7b75.firebasestorage.app/o/My%20videos%2Fmore%2FScreenshot%20(1422).png?alt=media&token=e4c4ca08-37f0-4dd3-8a9b-ea9f91cbd734)

![Hau Themes](https://firebasestorage.googleapis.com/v0/b/tija-a7b75.firebasestorage.app/o/My%20videos%2Fmore%2FScreenshot%20(1435).png?alt=media&token=5c226a5f-af93-4533-bb81-62aa3723c6f3)

![Hau jvm1](https://firebasestorage.googleapis.com/v0/b/tija-a7b75.firebasestorage.app/o/My%20videos%2FHau%2FScreenshot%20(1504).png?alt=media&token=fb83872b-a613-4ca8-9878-a46b4ba9cb1a)

![Hau jvm2](https://firebasestorage.googleapis.com/v0/b/tija-a7b75.firebasestorage.app/o/My%20videos%2Fmore%2FScreenshot%20(1457).png?alt=media&token=8ebc0e8e-9b30-42d4-ab7c-339bdf950234)

![Hau jvm3](https://firebasestorage.googleapis.com/v0/b/tija-a7b75.firebasestorage.app/o/My%20videos%2Fmore%2FScreenshot%20(1458).png?alt=media&token=a8ca5e5c-89c4-41c2-805b-a34ea474bf57)

![Hau jvm4](https://firebasestorage.googleapis.com/v0/b/tija-a7b75.firebasestorage.app/o/My%20videos%2Fmore%2FScreenshot%20(1459).png?alt=media&token=0da5fc98-667c-47af-a52e-ec9446dc7621)

![Hau both1](https://firebasestorage.googleapis.com/v0/b/tija-a7b75.firebasestorage.app/o/My%20videos%2FHau%2FScreenshot%20(1500).png?alt=media&token=b9478e33-a7fa-4e41-809d-7926013a670a)

![Hau both2](https://firebasestorage.googleapis.com/v0/b/tija-a7b75.firebasestorage.app/o/My%20videos%2FHau%2FScreenshot%20(1501).png?alt=media&token=bed64675-b604-41bf-9e9b-d5835d65d6bd)

![Hau both3](https://firebasestorage.googleapis.com/v0/b/tija-a7b75.firebasestorage.app/o/My%20videos%2FHau%2FScreenshot%20(1506).png?alt=media&token=b2baf796-b20b-4417-9270-ec2020ae5f04)

---

## ✨ Features

- 🎨 **Themes & Color Schemes**  
  Light, Dark, and custom accent themes with easy extension for brand‑specific styling.

- 💬 **Chat Detail Screens**  
  Dialog‑based message bubbles (pill‑shaped, frosted, rounded).  
  Support for text, emoji, and media messages.

- 🧠 **State Handling with ViewModels**  
  MVVM architecture using Kotlin coroutines and Flow.  
  Reactive UI updates and previewable states.

- 📂 **Repositories with Mock Data**  
  Prebuilt mock datasets for testing and demos.  
  Easily swappable with real backend (Firebase, REST, GraphQL).

- 🧭 **Navigation System**  
  Type‑safe sealed routes with `@Serializable`.  
  Bottom navigation bar with frosted glass effect.  
  Nested navigation: ChatList → ChatDetail → Settings → Sub‑screens.

---

## 🛠️ Project Structure

```
Hau/
 ├── composeApp/
 │    ├── src/
 │    │    ├── commonMain/      # Shared UI and logic
 │    │    ├── androidMain/     # Android-specific code
 │    │    ├── jsMain/          # JS target for web
 │    │    ├── jvmMain/         # Desktop target
 │    │    ├── wasmJsMain/      # WebAssembly target
 │    │    └── webMain/         # Web-specific overrides
 │    └── build/                # Compiled artifacts
 ├── gradle/
 │    ├── wrapper/
 │    └── libs.versions.toml
 ├── .idea/ .gradle/ .kotlin/   # IDE and build configs
 ├── README.md
 ├── build.gradle.kts
 ├── settings.gradle.kts
 └── local.properties
```

---

## 🧭 Navigation Overview

Hau defines all navigation destinations as a sealed interface `NavDestinaton` with `@Serializable` routes. This ensures type safety and easy deep‑linking.

### Example Route Definitions
```kotlin
@Serializable
sealed interface NavDestinaton { val routePattern: String }

@Serializable data object CHAT : NavDestinaton { override val routePattern = "CHAT" }
@Serializable data class DETAIL(val chatId: String) : NavDestinaton { override val routePattern = "DETAIL" }
@Serializable data object SETTINGS : NavDestinaton { override val routePattern = "SETTINGS" }
```

### Bottom Navigation Implementation
```kotlin
val bottomNavItems = listOf(
    BottomNavItem(Icons.Outlined.Forum, Icons.Default.Forum, Routes.CHAT),
    BottomNavItem(Icons.Outlined.Stream, Icons.Filled.Stream, Routes.MEMORIES),
    BottomNavItem(Icons.Outlined.Call, Icons.Filled.Call, Routes.CALLS),
    BottomNavItem(Icons.Outlined.Settings, Icons.Filled.Settings, Routes.SETTINGS)
)
```

### Supported Screens
- `CHAT` → Chat list with click to detail
- `DETAIL(chatId)` → Specific chat detail
- `CHANNEL_DETAIL(channelId)` → Group/channel detail
- `MEMORIES` → Memory channels
- `CALLS` → Call history
- `SETTINGS` → Settings root
- Sub‑settings: Privacy, Messaging, Notifications, Storage, Language, Help, Invite, Passkeys, Email, Delete Account, Request Info, Avatar, Account
- `VIDEO_CALL`, `AUDIO_CALL`, `SCHEDULE_CALL` → Call flows

---

## 🚀 Getting Started

### Prerequisites
- IntelliJ IDEA or Android Studio
- Kotlin Multiplatform plugin
- JDK 17+

### Build Commands

#### Android
```bash
./gradlew :composeApp:assembleDebug
```

#### Desktop
```bash
./gradlew :composeApp:run
```

#### Web (Wasm)
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

#### Web (JS)
```bash
./gradlew :composeApp:jsBrowserDevelopmentRun
```

---

## 📦 Sample Usage

```kotlin
val viewModel = ChatViewModel(repository = MockChatRepository())

ChatDetailScreen(
    viewModel = viewModel,
    theme = HauTheme.Dark
)
```

---

## 🤝 Contributing

We welcome contributions from the community!  
To get started:

1. Fork the repository  
2. Create a feature branch  
3. Submit a pull request  

Check out our issue templates and contribution guide.

---

## 📜 License

MIT License © 2026 Mugumya Ali

---

## 🔗 Resources

- Kotlin Multiplatform Docs (kotlinlang.org in Bing) [(bing.com in Bing)](https://www.bing.com/search?q="https%3A%2F%2Fwww.bing.com%2Fsearch%3Fq%3D%2522https%253A%252F%252Fkotlinlang.org%252Fdocs%252Fmultiplatform.html%2522")  
- Jetpack Compose Multiplatform (jetbrains.com in Bing) [(bing.com in Bing)](https://www.bing.com/search?q="https%3A%2F%2Fwww.bing.com%2Fsearch%3Fq%3D%2522https%253A%252F%252Fwww.jetbrains.com%252Flp%252Fcompose-multiplatform%252F%2522")  
- [Hau GitHub Repository](https://github.com/Alidon256/Hau)
```
