<p align="center">
  <img src="app/src/main/res/drawable/ic_launcher_foreground.xml" width="110" alt="Baltazar Logo"/>
</p>

<h1 align="center">Baltazar</h1>

<p align="center">
  A robust, scalable, and theme-driven multi-module Android application built with <b>Kotlin</b> and <b>Jetpack Compose</b>. Baltazar offers a comprehensive suite of services including Food delivery, Hotel booking, Travel tours, and Rent-a-car, all integrated into a single seamless experience.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose"/>
  <img src="https://img.shields.io/badge/Hilt-34A853?style=for-the-badge&logo=dagger&logoColor=white" alt="Hilt"/>
  <img src="https://img.shields.io/badge/Retrofit-48B983?style=for-the-badge&logo=square&logoColor=white" alt="Retrofit"/>
  <img src="https://img.shields.io/badge/KSP-7F52FF?style=for-the-badge" alt="KSP"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Coil-2C2C2C?style=flat-square" alt="Coil"/>
  <img src="https://img.shields.io/badge/DataStore-4285F4?style=flat-square" alt="DataStore"/>
  <img src="https://img.shields.io/badge/OkHttp-48B983?style=flat-square" alt="OkHttp"/>
  <img src="https://img.shields.io/badge/kotlinx.serialization-7F52FF?style=flat-square" alt="kotlinx.serialization"/>
  <img src="https://img.shields.io/badge/Navigation%20Compose-4285F4?style=flat-square" alt="Navigation Compose"/>
  <img src="https://img.shields.io/badge/Material3-777777?style=flat-square" alt="Material3"/>
  <img src="https://img.shields.io/badge/Timber-F4B400?style=flat-square" alt="Timber"/>
</p>

## 🏗️ Architecture Overview

The project follows a **Feature-based Multi-Module Architecture** combined with **Clean Architecture** principles. This ensures strict isolation of business logic, high modularity, and faster build times.

- **`app`**: The main entry point. Handles global navigation, Splash logic, and Hilt application configuration.
- **`core`**: The shared foundation module containing global UI components, design tokens (constants), common enums, and utility managers (Network, Cache).
- **`feature:*`**: Encapsulated business domains (Auth, Food, Hotel, etc.) that depend only on the `:core` module.

---

## 📁 Project Structure

```text
root/
├── app/                        # [Module] Entry point (NavGraph, MainActivity)
├── core/                       # [Module] Shared foundation
│   ├── components/             # Global UI atoms (CustomTextField, RoundedButton)
│   ├── constants/              # Design tokens (Paddings, Spaces, Durations)
│   ├── enums/                  # Global enums (Language, Region, ServiceType)
│   ├── managers/               # Logic managers (Network, Cache, Encryption)
│   └── theme/                  # Material3 Theme, Color, and Typography
│
└── feature/                    # [Namespace] Business Modules
    ├── auth/                   # Login, Register, Onboarding & Validations
    ├── explore/                # Main discovery hub and global search
    ├── food/                   # Foods listing, Detail and Company views
    ├── hotel/                  # Hotels search, Booking and Detail views
    ├── travel/                 # Travels packages and Detail views
    ├── rentacar/               # Rent-a-car listings and Specifications
    ├── taxi/                   # Ride hailing and tracking
    ├── profile/                # User Profile, Personal Info, and Wishlist
    └── order/                  # Centralized Checkout flow (Payment, Confirm)
```

### 🧱 Feature Module Layout
Each feature module is organized into a four-layer Clean Architecture structure:
- **`ui`**: Pager-based or Single-screen Composables with `State` + `ViewModel`.
- **`domain`**: Pure Kotlin business logic, Repository interfaces, and Domain models.
- **`data`**: Repository implementations, API services, and Serialization DTOs.
- **`core`**: Module-specific utilities like local validations or extensions.

---

## 🚀 Tech Stack

| Layer | Tools |
|---|---|
| **Language** | Kotlin (Coroutines, Flow) |
| **UI** | Jetpack Compose, Material3, Coil, Shimmer |
| **DI** | Dagger Hilt |
| **Networking** | Retrofit, OkHttp, Kotlinx Serialization |
| **Navigation** | Compose Navigation (Type-safe Routes) |
| **Storage** | DataStore, Room (Planned), EncryptedSharedPreferences |
| **Build** | Gradle Kotlin DSL, Version Catalog (TOML), KSP |

---

## 🎨 AI Coding Rules & Design System

To ensure codebase consistency and high quality, we follow these strict rules:
1. **Theme-Driven**: Every color and font must come from `MaterialTheme`. No hardcoded hex codes or `.sp` values.
2. **Zero Hardcoded Strings**: All user-facing text resides in `strings.xml` with full support for **English, Azerbaijani, and Turkish**.
3. **Design Tokens**: Spacing, padding, and durations are exclusively managed via `:core:constants`.
4. **Unidirectional Data Flow**: Data is passed to UI through immutable `State` models within `ViewModels`.

---

## 🌐 Backend Services

The mobile application communicates with the following backend services:

- **[Baltazar-Backend](https://github.com/MegrurNiftiyev/Baltazar-Backend)**: The primary REST API handling authentication and core business data (Food, Hotel, Travel, etc.).
- **[Baltazar-Payment-Backend](https://github.com/MegrurNiftiyev/Baltazar-Payment-Backend)**: A dedicated microservice for processing secure payments and order flows.

---

## 🛠️ Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/MegrurNiftiyev/Baltazar-Mobile.git
   ```
2. Open the project in **Android Studio Ladybug** (or newer).
3. Sync Gradle and run the `:app` module.

## 📄 License

This project is licensed under the MIT License.
