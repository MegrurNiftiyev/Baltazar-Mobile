<p align="center">
  <img src="assets/logo.png" width="140" alt="Baltazar Logo"/>
</p>

<h1 align="center">Baltazar Android Application</h1>

<p align="center">
  A robust, production-ready, and highly scalable multi-module Android application built with <b>Kotlin</b> and <b>Jetpack Compose</b>. Baltazar integrates a full ecosystem of services—including Food delivery, Hotel booking, Travel tours, Rent-a-car, and Taxi ride-hailing—into a single unified mobile experience.
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
</p>

<p align="center">
  <a href="assets/apk/baltazar.apk?raw=true">
    <img src="https://img.shields.io/badge/Download-APK-34A853?style=for-the-badge&logo=android&logoColor=white" alt="Download APK"/>
  </a>
</p>


## 📱 Screenshots

### 🚀 Splash & Onboarding

<p align="center">
  <img src="assets/screenshots/01_splash.jpg" width="190" alt="Splash"/>
  <img src="assets/screenshots/02_onboarding_welcome.jpg" width="190" alt="Onboarding - Welcome"/>
  <img src="assets/screenshots/03_onboarding_discover.jpg" width="190" alt="Onboarding - Discover"/>
  <img src="assets/screenshots/04_onboarding_hit_the_road.jpg" width="190" alt="Onboarding - Hit the Road"/>
  <img src="assets/screenshots/05_auth_welcome.jpg" width="190" alt="Login / Continue as Guest"/>
</p>

### 🧭 Explore & Food

<p align="center">
  <img src="assets/screenshots/06_explore_home.jpg" width="190" alt="Explore Home"/>
  <img src="assets/screenshots/07_food_list.jpg" width="190" alt="Food List"/>
  <img src="assets/screenshots/08_food_detail.jpg" width="190" alt="Food Detail"/>
  <img src="assets/screenshots/09_food_detail_dark.jpg" width="190" alt="Food Detail (Dark)"/>
  <img src="assets/screenshots/10_companies_list.jpg" width="190" alt="Companies"/>
  <img src="assets/screenshots/11_company_detail.jpg" width="190" alt="Company Detail"/>
</p>

### 🏨 Hotels & 🚗 Rent-a-car

<p align="center">
  <img src="assets/screenshots/12_hotels_filtered.jpg" width="190" alt="Hotels (Filtered)"/>
  <img src="assets/screenshots/13_hotel_detail_dark.jpg" width="190" alt="Hotel Detail & Rooms"/>
  <img src="assets/screenshots/19_rentacar_detail.jpg" width="190" alt="Rent-a-car Detail"/>
</p>

### ✈️ Travel & Tour Routes

<p align="center">
  <img src="assets/screenshots/14_travel_detail.jpg" width="190" alt="Travel Detail"/>
  <img src="assets/screenshots/15_travel_detail_gabala_sheki.jpg" width="190" alt="Travel Detail - Gabala & Sheki"/>
  <img src="assets/screenshots/16_travel_detail_dark.jpg" width="190" alt="Travel Detail (Dark)"/>
  <img src="assets/screenshots/17_tour_route_map.jpg" width="190" alt="Tour Route Map"/>
  <img src="assets/screenshots/18_tour_route_map_baku.jpg" width="190" alt="Tour Route Map - Baku"/>
</p>

### 👤 Profile, Wishlist & Settings

<p align="center">
  <img src="assets/screenshots/20_wishlist.jpg" width="190" alt="Wishlist"/>
  <img src="assets/screenshots/21_profile.jpg" width="190" alt="Profile"/>
  <img src="assets/screenshots/22_profile_guest.jpg" width="190" alt="Profile (Guest)"/>
  <img src="assets/screenshots/23_personal_information.jpg" width="190" alt="Personal Information"/>
  <img src="assets/screenshots/24_passport_information.jpg" width="190" alt="Passport Information"/>
  <img src="assets/screenshots/25_driver_license.jpg" width="190" alt="Driver License"/>
</p>

<p align="center">
  <img src="assets/screenshots/26_language_selection.jpg" width="190" alt="Language Selection"/>
  <img src="assets/screenshots/27_region_selection.jpg" width="190" alt="Region Selection"/>
  <img src="assets/screenshots/28_card_style_selection.jpg" width="190" alt="Card Style Selection"/>
  <img src="assets/screenshots/29_about_us.jpg" width="190" alt="About Us"/>
  <img src="assets/screenshots/30_help_support.jpg" width="190" alt="Help & Support"/>
  <img src="assets/screenshots/31_logout_dialog.jpg" width="190" alt="Log Out Dialog"/>
</p>


## 🏗️ 1. Architecture Overview

The project strictly follows a **Feature-based Multi-Module Clean Architecture**.

```text
root/
├── app/                        # Main entry point (Global NavHost, MainActivity, Hilt Application)
├── core/                       # Shared foundational library module (:core)
│   ├── components/             # Reusable UI atoms (CustomTextField, CustomAppBar, CustomAlertDialog)
│   ├── constants/              # Centralized Design Tokens (Paddings, Spaces, BorderRadiuses, IconSizes)
│   ├── enums/                  # Global domain enums (Language, Region, ServiceType, UserRole)
│   ├── managers/               # Centralized managers (SessionManager, AuthGateManager, CacheManager)
│   ├── navigation/             # Type-safe Navigation route definitions & screen contracts
│   └── theme/                  # Material3 Theme, Palette, Typography, and Shapes
│
└── feature/                    # Autonomous Feature Modules
    ├── auth/                   # Login, Register, Verification, Onboarding & Auth Gate flow
    ├── explore/                # Home discovery, global search & category navigation
    ├── food/                   # Food menu, service detail, company page & reviews
    ├── hotel/                  # Hotel discovery, room selection & booking flow
    ├── travel/                 # Tour packages, itineraries & destination booking
    ├── rentacar/               # Vehicle listings, specifications & rental checkout
    ├── taxi/                   # Ride hailing, real-time map tracking & fare calculation
    ├── profile/                # User profile, personal information, driver license & passport settings
    └── order/                  # Centralized Checkout flow (Map delivery selection, Payment, Confirmation)
```

Each feature module is internally structured into clean architectural layers:

```text
feature/<name>/src/main/java/com/example/baltazar/feature/<name>/
├── core/                  # Module-specific utilities, DI modules (Network & Repository), Navigation graphs
│   ├── di/                # Feature-specific AuthNetworkModule / AuthRepositoryModule
│   ├── mapper/            # Exception & DTO mappers
│   └── navigation/        # Sub-navigation graphs
├── data/                  # Data Layer
│   ├── datasources/       # Remote API services and Retrofit endpoints
│   ├── model/             # Request & Response DTO models
│   └── repository/        # Repository implementations
├── domain/                # Domain Layer (Pure Kotlin, zero Android dependencies)
│   ├── model/             # Domain entities
│   └── repository/        # Repository Interfaces
└── ui/                    # Presentation Layer
    ├── components/        # Atomic, pure UI components (No ViewModel dependencies)
    └── screens/           # Composables & ViewModels (State orchestration)
```

> 📌 **Module Coupling Rule**: Feature modules do not depend directly on each other; they only depend on `:core`. Shared models and cross-module infrastructure reside in `:core`.


## 🗺️ 2. Global Navigation Architecture

The application uses **Navigation Compose** with type-safe navigation routes.

```text
MainActivity
     │
     ▼
Global NavHost
     │
     ├── AuthNavGraph
     ├── ExploreNavGraph
     ├── FoodNavGraph
     ├── HotelNavGraph
     ├── TravelNavGraph
     ├── RentACarNavGraph
     ├── TaxiNavGraph
     ├── ProfileNavGraph
     └── OrderNavGraph
```

### Key Distinction:
- **NavGraph**: Maps routes to composable screens.
- **ViewModel / Repository**: Decides what the next business step is.

The navigation graph itself does not decide whether the user needs personal information, a driver's license, a delivery address, or payment.


## 🔒 3. Authentication Gate (`AuthGateManager`)

Guest users can browse public content freely. Protected actions require authentication (e.g. placing an order, writing a review, or opening profile settings).

```text
User performs protected action
          │
          ▼
SessionManager
          │
     Is user logged in?
       /           \
     YES            NO
      │              │
      ▼              ▼
Continue        AuthGateManager
                    │
                    ▼
              Save target route
                    │
                    ▼
              Login / Register
                    │
                    ▼
             Authentication OK
                    │
                    ▼
          Resolve pending route
                    │
                    ▼
          Original destination
```

```kotlin
authGateManager.requireAuth(
    nextScreen = TargetRoute
)
```

- **If authenticated**: `requireAuth()` executes the target action immediately.
- **If guest**: `requireAuth()` saves the target route, emits `AuthGateState.Required(targetRoute)`, routes to `AuthNavGraph`, and automatically redirects the user to the `targetRoute` upon successful login or registration.


## 🛒 4. Service Detail → Order Flow Convergence

Detail screens (Food, Hotel, Travel, Rent-a-car) do not construct the checkout process internally. They navigate directly to the centralized **Order Flow**:

```kotlin
onActionClick = {
    if (viewModel.isGuest()) {
        navController.navigate(Login(isPopStack = true))
    } else {
        navController.navigate(
            OrderFlow(
                serviceType = ServiceType.FOOD.name,
                serviceId = state.food.id
            )
        )
    }
}
```

```text
Food Detail      ──────┐
Hotel Detail     ──────┼────► OrderFlow (Centralized Checkout)
Travel Detail    ──────┤
Rent-a-car Detail ─────┘
```


## ⚡ 5. Order Flow Execution

The checkout flow is controlled by `OrderNavGraph`. The initial entry point is `OrderSummaryScreen`:

```text
OrderFlow ──► OrderSummaryScreen ──► OrderSummaryViewModel ──► createOrder() ──► getNextScreen(orderId)
```


## 🔄 6. Dynamic `next-screen` Mechanism

The client does **not** hardcode the checkout step order. After creating an order:

```text
POST /api/orders
      │
      ▼
GET /api/orders/{id}/next-screen
      │
      ▼
NextScreenType
```

The backend dynamically returns the exact required next step:
- `PERSONAL_INFO_SCREEN`
- `DRIVER_LICENSE_SCREEN`
- `PASSPORT_INFO_SCREEN`
- `DELIVERY_ADDRESS_SCREEN`
- `PAYMENT_SCREEN`
- `CONFIRM_SCREEN`
- `UNKNOWN`

```kotlin
fun NavHostController.navigateToNextScreen(
    screenType: NextScreenType,
    orderId: String
) {
    when (screenType) {
        NextScreenType.PERSONAL_INFO_SCREEN -> navigate(ProfilePersonalInfo(isFromOrder = true))
        NextScreenType.DRIVER_LICENSE_SCREEN -> navigate(ProfileDriverLicense(isFromOrder = true))
        NextScreenType.PASSPORT_INFO_SCREEN -> navigate(ProfilePassport(isFromOrder = true))
        NextScreenType.DELIVERY_ADDRESS_SCREEN -> navigate(OrderMapDeliverySelection(orderId = orderId))
        NextScreenType.PAYMENT_SCREEN -> navigate(OrderPayment(orderId = orderId))
        NextScreenType.CONFIRM_SCREEN -> navigate(OrderConfirm)
        NextScreenType.UNKNOWN -> Unit
    }
}
```

```text
Backend decides WHAT is required  ──►  NextScreenType  ──►  Navigation decides WHERE to go
```


## 🔄 7. Complete Dynamic Order Navigation Example

```text
┌─────────────────────┐
│    Order Summary    │
└──────────┬──────────┘
           │ getNextScreen()
           ▼
┌─────────────────────┐
│    Personal Info    │
└──────────┬──────────┘
           │ save / getNextScreen()
           ▼
┌─────────────────────┐
│   Driver License    │
└──────────┬──────────┘
           │ save / getNextScreen()
           ▼
┌─────────────────────┐
│    Passport Info    │
└──────────┬──────────┘
           │ save / getNextScreen()
           ▼
┌─────────────────────┐
│ Delivery / Map      │
└──────────┬──────────┘
           │ save address / getNextScreen()
           ▼
┌─────────────────────┐
│      Payment        │
└──────────┬──────────┘
           │ payment / getNextScreen()
           ▼
┌─────────────────────┐
│      Confirm        │
└─────────────────────┘
```


## 🔙 8. `popBackStack()` vs `next-screen`

These two navigation actions serve completely different purposes:

- **Forward Navigation (`next-screen`)**: Resolves the next business step determined by the backend (`getNextScreen()`).
- **Backward Navigation (`popBackStack()`)**: Pops the top destination off the back stack when the user presses Back, returning smoothly to the previous screen.

```text
next-screen    = Forward business flow resolution
popBackStack   = Backward stack traversal
```


## 📚 9. Order Flow Navigation Back Stack

Using normal `navigate()` calls without clearing previous destinations maintains the complete history:

```text
Navigation Back Stack:
┌─────────────────────────┐
│ Payment                 │ ← Current Screen
├─────────────────────────┤
│ DeliveryAddress         │
├─────────────────────────┤
│ DriverLicense           │
├─────────────────────────┤
│ PersonalInfo            │
├─────────────────────────┤
│ OrderSummary            │
└─────────────────────────┘
```

Calling `navController.popBackStack()` smoothly pops destination by destination.


## 🔁 10. Continuing Pending Orders from `OrdersScreen`

The `OrdersScreen` allows users to resume pending or incomplete orders:

```text
User taps pending order (PENDING / AWAITING_PAYMENT / PROCESSING)
                 │
                 ▼
      continueOrderFlow(order.id)
                 │
                 ▼
       getNextScreen(orderId)
                 │
                 ▼
      navigateToNextScreen(...)
```

Completed orders navigate directly to `OrderDetailScreen(order.id)`.


## 📍 11. Delivery Address / Map Flow (`MapDeliverySelection`)

`MapDeliverySelectionScreen` is an active business step in the order process handling location permission, map interaction, reverse geocoding, and address confirmation:

```text
MapDeliverySelection ──► patchDeliveryAddress(...) ──► Success ──► getNextScreen(orderId) ──► navigateToNextScreen(...)
```


## 💳 12. Payment Architecture & Component Decomposition

`PaymentScreen` delegates UI rendering to pure atomic components in `payment/components/`:

```text
PaymentScreen
  ├── SavedCardsSection ──► SavedCardItem
  ├── AddCardButton
  ├── OrderSummaryCard
  ├── PaymentBottomBar
  ├── AddCardBottomSheet
  └── PaymentErrorBottomSheet
```


## 💸 13. Payment Request Sequence

Payment execution involves two distinct remote steps:

```text
1. PATCH /api/orders/{id}/payment-method   (Attaches selected payment method to order)
2. POST  /api/payment/pay/{id}             (Authorizes payment transaction via gateway)
```

A successful payment-method update does not guarantee that the transaction authorization will succeed.


## ⚠️ 14. Payment Error Handling

If payment authorization fails (e.g. insufficient funds, HTTP 429, card declined):
1. The error message is captured by `PaymentViewModel`.
2. The UI opens `PaymentErrorBottomSheet` with options to **Try Again** or **Choose a Different Card**.
3. The app **never** forces local fallback navigation on failure; errors are surfaced to the user cleanly.




## 🌐 Backend Services Integration

- **[Baltazar-Backend](https://github.com/MegrurNiftiyev/Baltazar-Backend)**: Primary REST API powering authentication, catalog exploration, reviews, and order state machines.
- **[Baltazar-Payment-Backend](https://github.com/MegrurNiftiyev/Baltazar-Payment-Backend)**: Microservice managing card tokenization, payment method persistence, and gateway authorizations.


## 🛠️ Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/MegrurNiftiyev/Baltazar-Mobile.git
   ```
2. Open the project in **Android Studio Ladybug** (or newer).
3. Sync Gradle and run the `:app` configuration.


## 📄 License

This project is licensed under the **MIT License**.
