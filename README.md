# Baltazar - Modern Multi-Module Android App

Baltazar is a robust, scalable, and theme-driven Android application built with **Kotlin** and **Jetpack Compose**. The project follows a **Feature-based Multi-Module Architecture** combined with **Clean Architecture** principles to ensure maintainability and high-speed development.

---

## 🏗️ Architecture Overview

The app is divided into layers and features to isolate business logic from UI and data sources:

- **Multi-Module Structure**: Each business domain (Auth, Food, Hotel, etc.) is its own Gradle module.
- **Clean Architecture**: Within each module, code is organized into `ui`, `domain`, `data`, and `core` layers.
- **Theme-Driven UI**: Entire UI is controlled by `MaterialTheme`, using centralized constants for paddings, spaces, and durations.
- **Dependency Injection**: Powered by **Dagger Hilt** for modular and testable code.

---

## 📁 Project Structure

This project follows a strict multi-module hierarchy. Each `:feature:*` module is independent and depends only on the `:core` module.

```text
root/
├── app/                        # [Module] Entry point
│   └── src/main/java/com/example/baltazar/
│       ├── BaltazarApplication.kt   # Hilt Application class
│       ├── MainActivity.kt          # Single Activity entry
│       └── core/
│           ├── navigation/          # Main NavGraph setup
│           └── splash/              # Splash logic & ViewModels
│
├── core/                       # [Module] Shared foundation
│   └── src/main/java/com/example/baltazar/core/
│       ├── components/              # Global UI atoms (Buttons, TextFields)
│       ├── constants/               # Design tokens (Paddings, Spaces, Durations)
│       ├── enums/                   # Global enums (Language, Region, ServiceType)
│       ├── extensions/              # Kotlin property extensions
│       ├── managers/                # Logic managers (Network, Cache, Storage)
│       ├── navigation/              # Type-safe Route definitions (Serializable)
│       └── theme/                   # Material3 Theme, Color, Type, Shape
│
└── feature/                    # [Namespace] Encapsulated Business Domains
    ├── auth/                   # [Module] Login, Register, Onboarding, Validations
    ├── explore/                # [Module] Discovery hub & Global Search
    ├── food/                   # [Module] Food listings, Company & Detail views
    ├── hotel/                  # [Module] Hotel search, Booking & Details
    ├── travel/                 # [Module] Tour packages & Travel details
    ├── rentacar/               # [Module] Car rental listings & Specs
    ├── taxi/                   # [Module] Ride hailing & Taxi tracking
    ├── profile/                # [Module] Personal Info, Address & Wishlist
    └── order/                  # [Module] Centralized Checkout flow (Payment, Confirm)
```

### 🧱 Inside a Feature Module
Every feature module follows a standardized **Clean Architecture** internal layout:

```text
feature-module/
├── ui/
│   ├── screens/                # State-driven screen compositions
│   │   └── [feature_name]/
│   │       ├── [Name]Screen.kt     # UI Layout (Stateless/Stateful)
│   │       ├── [Name]ViewModel.kt  # Logic & State handling
│   │       └── [Name]State.kt      # Immutable UI State model
│   └── components/             # Reusable UI parts local to this feature
├── domain/
│   ├── repository/             # Abstractions (Interfaces)
│   ├── model/                  # Pure Business/Domain models
│   └── enums/                  # Feature-specific enums
├── data/
│   ├── repository/             # Repository implementations (Logic)
│   ├── datasource/             # API/Database interaction logic
│   └── model/dto/              # Serialization models (Request/Response)
└── core/                       # Internal utilities (Validations, Local Extensions)
```

---

## 🚀 Tech Stack

- **UI**: Jetpack Compose (1.7+)
- **Logic**: Kotlin Coroutines & Flow
- **DI**: Dagger Hilt (2.5+)
- **Navigation**: Compose Navigation (Type-safe routes)
- **Networking**: Retrofit & OkHttp (planned)
- **Serialization**: Kotlinx Serialization
- **Image Loading**: Coil
- **Local Storage**: DataStore & Room (planned)

---

## 🎨 Design System Rules

To maintain consistency, this project follows strict AI coding rules:
1. **No Hardcoding**: All colors/fonts come from `MaterialTheme`.
2. **Multi-language**: All strings are stored in `strings.xml` (AZ, TR, EN).
3. **Constants**: No raw `.dp` or `.ms`. Use `Paddings`, `Spaces`, and `AppDurations`.
4. **Model-Based**: Data is passed to Composables via dedicated State models.

---

## 🛠️ Getting Started

1. Clone the repository.
2. Open with **Android Studio Ladybug** or newer.
3. Sync Gradle and run the `:app` module.
