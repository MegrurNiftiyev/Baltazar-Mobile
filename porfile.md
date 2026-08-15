

# Profile & Settings Refactor — Android Implementation Plan (Baltazar)

**v1 — revised against actual codebase.**

This plan is based on the current Baltazar source files in the provided `merged (40–47).txt` files. The existing Profile implementation, `SessionManager`, cache managers, navigation, drawer, theme setup, and avatar-related components were reviewed before defining the refactor.

**Important correction:** the current `ProfileViewModel` must **not** continue calling `IUserRepository.getCurrentUser()` whenever the Profile screen opens. The app already has a shared `SessionManager` containing a `StateFlow<User>` and a guest user, so Profile should consume session state locally instead of performing an auth-required request on screen entry.  

---

## 1. Existing code to reuse — do not recreate

### 1.1 `SessionManager` — existing app-wide user source

```kotlin
// core/core/managers/SessionManager.kt — EXISTING

@Singleton
class SessionManager @Inject constructor() {

    companion object {
        val DEFAULT_GUEST_USER = User(...)
    }

    private val _user = MutableStateFlow<User>(DEFAULT_GUEST_USER)
    val user: StateFlow<User> = _user.asStateFlow()

    fun set(user: User)
    fun clear()
    fun update(block: (User) -> User)
}
```

Current implementation already provides:

* `StateFlow<User>`
* guest user
* `set(user)`
* `clear()`
* loading state. 

**Do not create another `UserSession`, `UserStateManager`, or profile cache.**

The target architecture is:

```text
ProfileScreen
    ↓
ProfileViewModel
    ↓
SessionManager.user
    ↓
StateFlow<User>
```

**No `GET /api/users/me` call from Profile screen.**

---

## 2. Main problem in the current Profile implementation

### 2.1 Current behavior — remove this pattern

Current `ProfileScreen` executes:

```kotlin
LaunchedEffect(Unit) {
    viewModel.loadUser()
}
```

and `ProfileViewModel` does:

```kotlin
userRepository.getCurrentUser()
```

This is currently an auth-required API call every time Profile opens.  

### Required behavior

Profile opening must be completely local:

```text
Guest:
Profile opens
→ SessionManager.user == DEFAULT_GUEST_USER
→ no API call
→ Login / Sign up banner shown

Logged in:
Profile opens
→ SessionManager.user contains authenticated user
→ no API call
→ user banner shown
```

Do not use `IUserRepository` inside the new `ProfileViewModel` unless a later requirement explicitly introduces a profile-refresh action.

---

# 3. ProfileViewModel refactor

## 3.1 Replace constructor dependency

### Current

```kotlin
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : ViewModel()
```

### Target

```kotlin
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val settingsRepository: ISettingsRepository
) : ViewModel()
```

Use the actual repository interface name chosen in Section 5 below.

---

## 3.2 New `ProfileState`

Replace the current state:

```kotlin
data class ProfileState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
```

The Profile screen should not represent the user as nullable/loading from a network request anymore.

Target:

```kotlin
data class ProfileState(
    val user: User = SessionManager.DEFAULT_GUEST_USER,
    val isDarkMode: Boolean = false,
    val selectedLanguage: String = "az",
    val isLanguageSheetOpen: Boolean = false
)
```

Do not add `isLoading` for user loading.

Do not add `error` for initial user loading.

The session already exists before Home/Profile is displayed because splash/session initialization is already performed centrally. 

---

## 3.3 User state must come from SessionManager

Expose the session flow directly or collect it into Profile state.

Preferred implementation:

```kotlin
private val _state = MutableStateFlow(ProfileState())
val state: StateFlow<ProfileState> = combine(
    sessionManager.user,
    settingsRepository.isDarkMode,
    settingsRepository.language
) { user, isDarkMode, language ->
    ProfileState(
        user = user,
        isDarkMode = isDarkMode,
        selectedLanguage = language
    )
}.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = ProfileState()
)
```

The exact implementation may use individual collectors instead of `combine`, but the important rule is:

**user state comes from `SessionManager.user`, not `IUserRepository.getCurrentUser()`.**

---

# 4. ProfileScreen redesign

## 4.1 Remove current profile content

The current screen contains:

* large standalone avatar
* name/email section
* Personal Details card
* verification status section
* passport status
* driver license status. 

These should not remain as the main Profile settings screen.

Do not keep the old verification cards underneath the new tiles unless explicitly required later.

---

# 5. New SettingsRepository in `core`

Create:

```text
core/
└── domain/
    └── repository/
        └── ISettingsRepository.kt

core/
└── data/
    └── repository/
        └── SettingsRepository.kt
```

Or follow the exact repository package convention already used by other core repositories if the final source tree uses a different placement.

---

## 5.1 Repository responsibilities

`SettingsRepository` handles only local settings/session actions:

```kotlin
interface ISettingsRepository {

    val isDarkMode: Flow<Boolean>

    val language: Flow<String>

    suspend fun setDarkMode(enabled: Boolean)

    suspend fun setLanguage(language: String)

    suspend fun logout()
}
```

Do not add remote API calls.

---

## 5.2 Theme persistence

Use the existing `CacheManager`.

`CacheManager` already supports persistent `Boolean` and `String` values through DataStore. 

Add constants to the existing cache key structure:

```kotlin
const val IS_DARK_MODE = "is_dark_mode"
const val APP_LANGUAGE = "app_language"
```

Do not create another preferences manager just for theme.

Example:

```kotlin
override val isDarkMode: Flow<Boolean>
    get() = cacheManager.getBoolean(
        CacheKeys.IS_DARK_MODE,
        false
    )

override suspend fun setDarkMode(enabled: Boolean) {
    cacheManager.setBoolean(
        CacheKeys.IS_DARK_MODE,
        enabled
    )
}
```

---

## 5.3 Language persistence

Use the existing `CacheManager` for the selected language.

However, the codebase already contains `AppPreferences` which persists language and region and is used by `LocalizationInterceptor`. 

**Do not silently create two independent language sources.**

During implementation:

1. inspect whether `LocalizationInterceptor` is the authoritative runtime language source;
2. if `AppPreferences` is still required by networking, `SettingsRepository.setLanguage()` must update the existing language source as well;
3. `CacheManager` may remain the persistence source required by the Profile settings specification, but both values must stay synchronized.

Do not leave the app in a state where Profile says `ru` but network headers still use `az`.

---

# 6. Logout behavior

Logout must be local-only.

## 6.1 No logout API

Do not add:

```kotlin
POST /logout
```

Do not call any remote auth endpoint.

---

## 6.2 Remove encrypted auth state

The existing `EncryptedCacheManager` already stores access and refresh tokens. 

Logout must remove:

```kotlin
CacheKeys.ACCESS_TOKEN
CacheKeys.REFRESH_TOKEN
```

Use the existing:

```kotlin
encryptedCacheManager.removeSecureKey(...)
```

Do not clear the entire encrypted preferences store unless the current project confirms that it contains nothing else.

---

## 6.3 Clear local session

After token removal:

```kotlin
sessionManager.clear()
```

This resets the current user to:

```kotlin
SessionManager.DEFAULT_GUEST_USER
```

which already exists. 

---

## 6.4 Login state

Also update the existing login state:

```kotlin
cacheManager.setBoolean(
    CacheKeys.IS_LOGIN_FINISHED,
    false
)
```

The current splash flow already checks this key when deciding whether authentication is finished. 

---

## 6.5 Profile tile behavior

Logout tile must:

```text
tap
→ immediate logout
→ no confirmation dialog
→ clear tokens
→ clear session
→ update login state
→ navigate to Login
```

Do not show a confirmation dialog.

---

# 7. Theme — app-wide source of truth

## 7.1 Do NOT create `SettingsViewModel`

The separate `SettingsViewModel` pattern from another project is not to be copied.

Theme and language remain inside `ProfileViewModel`.

However, theme itself must be observable at app root.

---

## 7.2 `MainActivity` must observe theme

Current root is:

```kotlin
BaltazarAppTheme {
    ...
}
```

inside `MainActivity`. 

Refactor so the root observes `SettingsRepository.isDarkMode`.

Preferred shape:

```kotlin
val isDarkMode by settingsRepository.isDarkMode
    .collectAsStateWithLifecycle(initialValue = false)

BaltazarAppTheme(
    darkTheme = isDarkMode
) {
    ...
}
```

The exact dependency retrieval may use a root/app-level ViewModel or another architecture-consistent mechanism, but:

**MaterialTheme must be driven from an app-wide reactive theme source.**

Do not keep theme state only inside `ProfileScreen`.

---

## 7.3 Profile Theme tile

The Theme row is inline:

```text
Theme                          [ switch ]
```

No navigation.

The switch calls:

```kotlin
viewModel.changeTheme()
```

which writes to `SettingsRepository`.

Example:

```kotlin
fun changeTheme(enabled: Boolean) {
    viewModelScope.launch {
        settingsRepository.setDarkMode(enabled)
    }
}
```

Do not maintain a second Compose-only theme boolean disconnected from persistence.

---

# 8. Language bottom sheet

## 8.1 Supported values

Exactly:

```text
Az
Ing
Rus
```

Use the project's actual language enum if available instead of introducing a duplicate enum.

---

## 8.2 Profile state

```kotlin
val isLanguageSheetOpen: Boolean
val selectedLanguage: String
```

Methods:

```kotlin
fun openLanguageSheet()
fun closeLanguageSheet()
fun setLanguage(language: String)
```

---

## 8.3 Bottom sheet behavior

Tap `Language`:

```text
Language
    ↓
ModalBottomSheet
    ↓
Azerbaijani
English
Russian
```

Selecting a language:

1. persist it through `SettingsRepository`;
2. update any existing localization source such as `AppPreferences`;
3. close the sheet;
4. do not navigate to another screen.

---

# 9. Profile Banner

Create a dedicated component.

Recommended location:

```text
feature/profile/ui/screens/profile/
└── components/
    └── ProfileBanner/
        ├── ProfileBanner.kt
        └── components/
```

Follow the project convention that newly created components get their own folder.

---

## 9.1 Guest banner

When:

```kotlin
user.role == "GUEST"
```

show:

```text
[UserAvatar]   Login / Sign up
               Continue with your account
```

or equivalent final UI text based on existing resources.

Click:

```kotlin
navController.navigate(AuthSelection)
```

Use the existing auth navigation route, not a new authentication route. Existing auth navigation already contains `AuthSelection`, `Login`, and `Register`. 

No API call before navigation.

---

## 9.2 Logged-in banner

When user is authenticated:

```text
[UserAvatar]   User Name
               user@email.com
```

Click:

```kotlin
navController.navigate(ProfilePersonalInfo)
```

The current navigation graph already has the `ProfilePersonalInfo` route. 

---

# 10. UserAvatar — new core component

Create a centralized component under core:

```text
core/
└── core/
    └── components/
        └── UserAvatar/
            ├── UserAvatar.kt
            └── components/
```

---

## 10.1 Responsibilities

`UserAvatar` owns **all** avatar fallback behavior.

Inputs should be kept minimal and based on the existing `User` model.

Preferred:

```kotlin
@Composable
fun UserAvatar(
    user: User,
    modifier: Modifier = Modifier
)
```

Do not pass a different image URL manually from every screen unless the current `User` model makes that unavoidable.

---

## 10.2 Avatar behavior

```text
user.avatarUrl == null / blank
        ↓
default avatar from core res

valid URL
        ↓
load image

URL fails
        ↓
same default avatar from core res
```

Use the existing image-loading library already used by the project (`Coil` / `AsyncImage`).

No shimmer.

No border.

Always round.

No optional radius parameter.

No per-screen null handling.

---

## 10.3 Important existing inconsistency to fix

`SessionManager.set()` currently converts a missing avatar into a remote Unsplash URL. 

Do not keep this behavior as the final avatar source.

The default avatar must be owned by `UserAvatar`.

Refactor so a missing `avatarUrl` remains nullable/blank in the user model/session state rather than being replaced with a fake remote default URL.

---

# 11. Replace avatar usage throughout the app

Search all usages of:

```text
CircularImage
AsyncImage
avatarUrl.orEmpty()
Icons.Default.Person
```

where the UI represents the application's user avatar.

Replace those usages with:

```kotlin
UserAvatar(user = user)
```

At minimum verify:

```text
App Bar
Drawer / Sidebar
Explore
Profile
```

The current drawer uses `CircularImage` and applies a border. 

That must be replaced.

---

# 12. Drawer / Sidebar refactor

Current `ExploreSidebar` passes:

```kotlin
userName = user.name
imageUrl = user.avatarUrl.orEmpty()
isGuest = user.role == "GUEST"
```

and `UserTile` manually owns avatar rendering.  

Refactor:

```kotlin
UserTile(
    user = user,
    onClick = onUserClick
)
```

Inside:

```kotlin
UserAvatar(user = user)
```

---

## 12.1 Sidebar content

Logged-in:

```text
Name
Email
```

Guest:

```text
Login / Sign up
```

Do not invent a second user identity model.

The `User` domain model already contains `name`, `email`, `role`, and `avatarUrl`.

---

# 13. Profile tiles

Create a reusable profile tile component:

```text
ProfileTile/
└── ProfileTile.kt
```

Suggested shape:

```kotlin
@Composable
fun ProfileTile(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
)
```

No navigation logic inside the reusable tile.

---

## 13.1 Exact order

The screen must render exactly:

```text
1. Personal Info
2. Theme
3. Language
4. Help & Support
5. Log Out
```

---

## 13.2 Personal Info

```kotlin
onClick = {
    navController.navigate(ProfilePersonalInfo)
}
```

Reuse existing route.

---

## 13.3 Theme

Inline switch.

No navigation.

```kotlin
Switch(
    checked = state.isDarkMode,
    onCheckedChange = viewModel::changeTheme
)
```

---

## 13.4 Language

Click opens `ModalBottomSheet`.

No navigation.

---

## 13.5 Help & Support

Reuse existing Help navigation route.

Do not create a duplicate help screen.

---

## 13.6 Log Out

No dialog.

No confirmation.

Immediate:

```kotlin
viewModel.logout()
```

---

# 14. Remove obsolete Profile tiles/content

Do not keep these as Profile settings tiles:

```text
My Orders
Wishlist
Payment Methods
```

Orders and Wishlist are already represented at Home/bottom navigation level. The existing Home screen already defines Orders, Wishlist, and Profile tabs. 

Payment Methods must not be introduced as a new Profile tile.

---

# 15. Navigation behavior after logout

When logout completes:

```text
Profile
  ↓
logout()
  ↓
SessionManager.clear()
  ↓
tokens removed
  ↓
IS_LOGIN_FINISHED = false
  ↓
navigate to Login/AuthSelection
```

Avoid leaving the authenticated Profile destination in the back stack.

Preferred navigation:

```kotlin
navController.navigate(AuthSelection) {
    popUpTo<Home> {
        inclusive = true
    }
}
```

Adjust the `popUpTo` target to the actual current navigation graph structure after inspecting `AppNavGraph`.

Do not blindly assume a route exists; verify against the existing graph before implementation.

---

# 16. Splash / session initialization — do not break it

The current splash flow already:

1. reads onboarding state;
2. reads login state;
3. if logged in, loads user;
4. stores the user in `SessionManager`;
5. otherwise stores guest user. 

Do not remove this behavior as part of the Profile refactor.

The change is specifically:

```text
Splash/session initialization
    → allowed to establish session

Profile screen
    → must NOT re-fetch user
```

This distinction is important.

---

# 17. API calls allowed / forbidden

## Profile screen

Allowed:

```text
NONE
```

on initial render.

Forbidden:

```text
GET /api/users/me
PATCH /api/users/me
POST /logout
```

unless explicitly triggered by a separate user action in an existing flow.

---

# 18. Existing user repository remains intact

Do not delete `IUserRepository`.

It remains responsible for:

```kotlin
getCurrentUser()
updateProfile(...)
updatePhoto(...)
updatePassport(...)
updateDriverLicense(...)
```

The existing interface is already defined in core. 

The refactor only removes its use from the initial Profile screen state loading.

---

# 19. Existing Personal Info screen remains intact

Do not rewrite `PersonalInfoScreen` as part of this task.

Profile should only navigate to the existing screen:

```kotlin
navController.navigate(ProfilePersonalInfo)
```

The current navigation graph already attaches that route to `PersonalInfoScreen`. 

---

# 20. Suggested implementation sequence

### Phase 1 — Core settings

* [ ] Add `CacheKeys.IS_DARK_MODE`
* [ ] Add `CacheKeys.APP_LANGUAGE`
* [ ] Create `ISettingsRepository`
* [ ] Create `SettingsRepository`
* [ ] Add Hilt binding for settings repository

### Phase 2 — UserAvatar

* [ ] Add default avatar resource under core `res`
* [ ] Create `UserAvatar`
* [ ] Move fallback behavior out of individual screens
* [ ] Remove remote Unsplash fallback from `SessionManager`
* [ ] Replace avatar usage in Profile
* [ ] Replace avatar usage in Drawer
* [ ] Replace avatar usage in Explore/App Bar

### Phase 3 — Profile state

* [ ] Remove `IUserRepository` from `ProfileViewModel`
* [ ] Inject `SessionManager`
* [ ] Inject `ISettingsRepository`
* [ ] Replace nullable user loading state
* [ ] Expose `SessionManager.user`
* [ ] Remove `loadUser()` from Profile initialization

### Phase 4 — Profile UI

* [ ] Replace current Profile content
* [ ] Add Profile Banner
* [ ] Add Personal Info tile
* [ ] Add Theme switch
* [ ] Add Language tile
* [ ] Add Language bottom sheet
* [ ] Add Help & Support tile
* [ ] Add Log Out tile
* [ ] Remove My Orders
* [ ] Remove Wishlist
* [ ] Remove Payment Methods

### Phase 5 — App-wide theme

* [ ] Connect `SettingsRepository.isDarkMode` to app root
* [ ] Update `MainActivity`
* [ ] Confirm all screens react to theme changes immediately
* [ ] Do not introduce `SettingsViewModel`

### Phase 6 — Logout

* [ ] Remove access token
* [ ] Remove refresh token
* [ ] Set `IS_LOGIN_FINISHED = false`
* [ ] `SessionManager.clear()`
* [ ] Navigate to auth flow
* [ ] Confirm no logout API is triggered

---

# 21. Files expected to change

The exact package path must follow the existing source tree, but the implementation should primarily touch these areas:

```text
core/
├── core/constants/CacheKeys.*
├── core/managers/SessionManager.kt
├── core/components/UserAvatar/
├── data/repository/SettingsRepository.kt
└── domain/repository/ISettingsRepository.kt

feature/profile/
└── ui/screens/profile/
    ├── ProfileScreen.kt
    ├── ProfileState.kt
    ├── ProfileViewModel.kt
    └── components/
        ├── ProfileBanner/
        ├── ProfileTile/
        └── LanguageBottomSheet/

feature/explore/
└── ui/components/
    ├── ExploreSidebar.kt
    └── UserTile.kt

app/root:
└── MainActivity.kt
```

Also modify/add the corresponding Hilt modules and string resources required by the existing project conventions.

---

# 22. Things that must NOT be added

Do **not** create:

```text
SettingsViewModel
UserSessionManager
ProfileUserRepository
ProfileApiService
LogoutApiService
ThemeViewModel
LanguageViewModel
PaymentMethodsTile
WishlistTile
OrdersTile
```

unless a compile/runtime investigation proves that an existing architecture requires one of them.

The feature should reuse the current core architecture instead of introducing parallel state systems.

---

# 23. Acceptance checklist

## Profile / Guest

* [ ] Profile opens successfully when user is guest
* [ ] No `getCurrentUser()` API request occurs on Profile initial render
* [ ] No 401 is generated just by opening Profile
* [ ] Guest banner shows Login / Sign up
* [ ] Guest banner navigates to existing auth flow

## Logged-in user

* [ ] Profile reads user from `SessionManager`
* [ ] Avatar, name and email are displayed
* [ ] Clicking banner navigates to existing Personal Info route

## Avatar

* [ ] `UserAvatar` exists in core
* [ ] Default avatar comes from core resources
* [ ] `null` avatar uses default resource
* [ ] broken image URL uses the same fallback
* [ ] no border
* [ ] round shape
* [ ] no shimmer
* [ ] no per-screen avatar null checks
* [ ] Drawer uses `UserAvatar`
* [ ] Explore uses `UserAvatar`
* [ ] Profile uses `UserAvatar`

## Theme

* [ ] Theme switch exists inline
* [ ] Theme is persisted
* [ ] theme survives app restart
* [ ] theme change affects the whole application immediately
* [ ] app root observes repository state
* [ ] no `SettingsViewModel` introduced

## Language

* [ ] Language tile opens bottom sheet
* [ ] Azerbaijani option exists
* [ ] English option exists
* [ ] Russian option exists
* [ ] selected language persists
* [ ] existing `AppPreferences` / localization behavior stays synchronized
* [ ] selecting language closes the sheet

## Logout

* [ ] no confirmation dialog
* [ ] no logout API request
* [ ] access token removed
* [ ] refresh token removed
* [ ] login-finished flag reset
* [ ] `SessionManager.clear()` called
* [ ] user becomes guest immediately
* [ ] user is returned to authentication flow
* [ ] authenticated Profile screen cannot remain in back stack

## Regression checks

* [ ] Splash session initialization still works
* [ ] Personal Info route still works
* [ ] Help route still works
* [ ] Drawer still opens/closes correctly
* [ ] existing bottom navigation remains untouched except where required
* [ ] no duplicate user/session/theme state introduced
* [ ] project builds without introducing a second cache/preferences system


24. Profile layout — exact structure from the provided design reference

The Profile screen must follow the provided design reference as the visual and structural baseline.

The current Profile implementation is not the target UI. Rebuild the screen around the following two sections.

24.1 First section — Profil

This section contains exactly these three tiles:

Hesab növü
Bəyənilənlər
Təhlükəsizlik

Do not treat these as static UI rows. Each tile must have a real destination or action.

24.1.1 Hesab növü

Purpose:

Show the user's current account type.
Allow the user to reach the existing location where account/profile type can be updated.
The trailing value should display the current type when available, for example Təşkilatçı.

Navigation rule:

Hesab növü
    ↓
existing account/profile-type editing destination

Before implementation, inspect the existing ProfileUserDetail screen and its state/model.

The existing navigation graph already contains:

@Serializable
object ProfileUserDetail

and currently maps it to:

composable<ProfileUserDetail> {
    UserDetailScreen(navController)
}

Do NOT automatically assume ProfileUserDetail is the final destination.

Verify whether UserDetailScreen actually contains account-type editing.

Decision:

IF UserDetailScreen already edits account type
    → reuse ProfileUserDetail
ELSE
    → create a dedicated ProfileAccountType route/screen

Do not create duplicate account-type state if the existing user/profile model already contains the value.

24.1.2 Bəyənilənlər

Purpose:

Navigate to the user's Wishlist/Favorites screen.

This must reuse the existing Wishlist implementation.

The codebase already contains:

@Serializable
object Wishlist

and:

composable<Wishlist> {
    WishlistScreen(navController)
}

Therefore the tile must use:

navController.navigate(Wishlist)

Do NOT create another Favorites/Wishlist screen.

Do NOT create another repository for this tile.

The existing Wishlist feature and bottom navigation already use the same destination.

24.1.3 Təhlükəsizlik

Purpose:

Provide access to security/account-security controls.

The current source does not expose a confirmed dedicated Profile Security route.

Therefore implementation must first search the project for:

SecurityScreen
Password
ChangePassword
Security
AccountSecurity
ResetPassword
ChangePasswordScreen

Decision:

IF an existing security screen/route exists
    → reuse it

ELSE
    → create:
       ProfileSecurity route
       SecurityScreen
       SecurityViewModel only if actual state/business logic requires it

Do not use ProfileUserDetail for security merely because it already exists.

Security navigation must lead to a screen where the user can actually manage the relevant security settings.

Do not create an empty placeholder screen just to satisfy navigation.

25. Profile layout — second section Ayarlar

The second section must contain exactly:

Açıq/Qaranlıq rejim
Dil
Kart stili
Haqqında
Çıxış

The visual order must remain fixed.

26. Theme tile — inline switch

The Theme row remains a direct inline control.

Açıq rejim / Qaranlıq rejim       [ Switch ]

Rules:

no navigation
no bottom sheet
use ProfileViewModel
persist through ISettingsRepository
app root observes the same repository flow
changing it updates the entire application immediately

Do not introduce SettingsViewModel.

27. Language tile — ModalBottomSheet

The Language row opens a modal bottom sheet.

Use the provided Language bottom-sheet screenshot as the visual reference.

The bottom sheet should contain:

Dil

[ 🇺🇸 ]   [ 🇦🇿 ]   [ 🇹🇷 ]   [ 🇷🇺 ]

Use the application's actual supported language set.

The important UI characteristics from the reference are:

rounded top corners
dimmed background
centered drag handle
sheet title
compact selectable language cards
selected item has the application primary/green accent
unselected items use the normal surface container
selecting an item immediately updates the selected language
sheet closes after selection

Do not create a completely different modal style for Language.

Reuse the same bottom-sheet design system for the new Kart stili sheet below.

28. Kart stili — NEW setting

Add a new Profile setting tile:

Kart stili

This tile must NOT navigate to a normal screen.

It opens a ModalBottomSheet.

The provided Language bottom sheet must be used as the structural reference for this sheet.

Target interaction:

Kart stili
    ↓
ModalBottomSheet
    ↓
Grid
List
28.1 Card Style bottom sheet

Create:

feature/profile/
└── ui/
    └── screens/
        └── profile/
            └── components/
                └── CardStyleBottomSheet/
                    └── CardStyleBottomSheet.kt

Do not place the entire implementation directly inside ProfileScreen.kt.

The component must be independently reusable.

28.2 Selection model

Create a small reusable enum in core only if the selection is needed across modules:

enum class CardViewMode {
    GRID,
    LIST
}

Suggested package:

core/core/enums/CardViewMode.kt

Do not create multiple enums for the same concept.

28.3 Persistence

Persist the selected value using the same existing local settings architecture.

Add a cache key:

const val CARD_VIEW_MODE = "card_view_mode"

The value may be persisted as:

GRID
LIST

or another stable representation consistent with the existing cache conventions.

Do not use an in-memory-only remember { mutableStateOf(...) }.

The selection must survive app restart.

28.4 Repository API

Extend the settings repository:

interface ISettingsRepository {

    val isDarkMode: Flow<Boolean>

    val language: Flow<String>

    val cardViewMode: Flow<CardViewMode>

    suspend fun setDarkMode(enabled: Boolean)

    suspend fun setLanguage(language: String)

    suspend fun setCardViewMode(mode: CardViewMode)

    suspend fun logout()
}

ProfileViewModel remains the owner of settings actions.

Do not create a separate CardViewModeViewModel.

29. Card Style bottom sheet visual behavior

Use the Language sheet screenshot as the component reference.

Target:

┌───────────────────────────────────┐
│                ───                │
│                                   │
│  Kart stili                       │
│                                   │
│   ┌──────────┐   ┌──────────┐     │
│   │  GRID    │   │  LIST    │     │
│   │  ▦       │   │  ≡       │     │
│   └──────────┘   └──────────┘     │
│                                   │
└───────────────────────────────────┘

Exact visual implementation must follow the project's Material 3 theme tokens and the provided screenshots rather than introducing arbitrary colors.

Selected option:

primary accent
+ visible selected border/container

Unselected option:

surface container
+ normal icon/text

Only one option can be selected at a time.

30. Card View Mode must affect actual card rendering

This setting is NOT a cosmetic Profile-only preference.

It must change the presentation of service cards wherever the application uses the configurable card/list presentation.

The current Explore implementation renders service sections through:

ExploreSectionRow(...)

inside the main vertical list.

Before implementing the rendering switch:

inspect ExploreSectionRow;
inspect StandardItemCard;
inspect all screens that render ServiceCardItem;
identify the shared rendering component;
integrate the CardViewMode there instead of duplicating separate layout logic in every screen.

Existing StandardItemCard is already a shared card component and should be reused rather than creating a second equivalent card model/component.

31. Grid mode

When:

CardViewMode.GRID

the relevant service collection should be rendered using a 2-column grid where the current screen's content and card dimensions support it.

Preferred Compose structure:

LazyVerticalGrid(
    columns = GridCells.Fixed(2)
)

Do not automatically convert every vertical screen in the application into a grid.

Only screens/sections whose content is based on the shared service-card presentation should respond to this setting.

32. List mode

When:

CardViewMode.LIST

the same service items should be rendered as a vertical list.

Preferred structure:

LazyColumn {
    items(...)
}

Reuse the same domain data and click/navigation behavior.

Do not duplicate the repository/data layer.

33. Card click behavior must stay unchanged

Changing GRID → LIST must not change navigation.

The same item must still navigate to the same service destination.

Current Explore navigation is already based on ServiceCardItem.serviceType and serviceId, for example:

ServiceType.RENT_A_CAR ->
    navController.navigate(
        RentACarDetail(item.serviceId)
    )

ServiceType.HOTEL ->
    navController.navigate(
        HotelDetail(item.serviceId)
    )

ServiceType.TRAVEL ->
    navController.navigate(
        TravelDetail(item.serviceId)
    )

ServiceType.FOOD ->
    navController.navigate(
        FoodCompanyDetail(item.serviceId)
    )

Reuse this logic.

34. Card style must update reactively

Do not require the user to restart the app.

Target:

User opens Profile
    ↓
Kart stili
    ↓
List
    ↓
select List
    ↓
sheet closes
    ↓
service-card UI updates automatically

And:

List
    ↓
Profile
    ↓
Kart stili
    ↓
Grid
    ↓
sheet closes
    ↓
service-card UI switches back to Grid

This requires an app-wide observable state.

Use:

settingsRepository.cardViewMode

as the single source of truth.

Do not create:

ProfileState.cardViewMode
ExploreState.cardViewMode
WishlistState.cardViewMode

as independent persisted states.

Each relevant screen should collect the repository flow.

35. About tile

Haqqında continues to navigate to the existing About destination.

The codebase already contains:

@Serializable
object About

Reuse it.

Do not create another About route.

The provided About bottom-sheet screenshot is also a visual reference for the application's modal design style, but this refactor must not turn the existing About behavior into a duplicate screen.

36. Logout tile

Çıxış stays the last tile.

Rules:

no confirmation dialog
no bottom sheet
no API call
immediate execution
remove access token
remove refresh token
clear login state
clear SessionManager
return to authentication flow
37. Exact final Profile structure

The final screen should structurally be:

Profile

[ Back ]                              [ UserAvatar ]

Hello / user information

┌─────────────────────────────────────┐
│ Profil                              │
│                                     │
│  ♙  Hesab növü                >     │
│                                     │
│  ♡  Bəyənilənlər              >     │
│                                     │
│  🔒 Təhlükəsizlik              >    │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│ Ayarlar                             │
│                                     │
│  ☾  Açıq/Qaranlıq rejim       [ ]   │
│                                     │
│  ◎  Dil                       >     │
│                                     │
│  ▦  Kart stili                 >     │
│                                     │
│  ⓘ  Haqqında                  >     │
│                                     │
│  ⇥  Çıxış                      >     │
└─────────────────────────────────────┘

Do not recreate the exact screenshot text such as greeting, coins, version/footer unless those pieces already belong to the current Profile product requirements.

The screenshot is primarily a layout/component/interaction reference, not a requirement to copy unrelated data.

38. Existing code to reuse

Reuse:

SessionManager
CacheManager
EncryptedCacheManager
AppPreferences
IUserRepository
ProfileUserDetail route
Wishlist route
About route
Help route
StandardItemCard
existing service navigation
existing Material 3 theme
existing Snackbar infrastructure

The existing CacheManager already supports persistent Boolean/String values through DataStore.

The existing AppPreferences already stores language/region and is read by LocalizationInterceptor, so Language implementation must stay synchronized with that system.

39. New components/routes that may be required

Only add these when the existing codebase does not already provide an equivalent:

CardViewMode
CardStyleBottomSheet
ProfileAccountType route/screen
ProfileSecurity route/screen

Do not create duplicates.

For example:

IF existing account-type editor exists
    → reuse it

IF existing security screen exists
    → reuse it

IF no equivalent exists
    → create the missing destination
40. Updated acceptance checklist
Profile sections

Profile has a Profil section

Hesab növü exists

Bəyənilənlər exists

Təhlükəsizlik exists

all three have real navigation/action behavior

Account type

existing account-type editor was searched for before creating a new route

current account type is displayed when available

edit destination actually allows the user to change the relevant value

Wishlist

Bəyənilənlər uses existing Wishlist route

no duplicate Wishlist screen/repository created

Security

existing security implementation searched before adding one

security tile navigates to a real functional destination

no empty placeholder navigation

Theme

inline switch

persistence

app-wide reactive update

no SettingsViewModel

Language

Language opens ModalBottomSheet

bottom sheet follows provided screenshot style

selection persists

localization state remains synchronized

Card style

Kart stili tile exists

opens ModalBottomSheet

sheet visually follows Language sheet pattern

Grid option exists

List option exists

selected option is visually indicated

selection persists

selection updates app UI without restart

no duplicate card-view state exists

Grid/List rendering

shared service-card rendering path was identified before changing individual screens

Grid uses appropriate multi-column layout

List uses appropriate vertical layout

same domain model is reused

same click/navigation behavior is preserved

StandardItemCard is reused or extended instead of duplicated

About / Logout

About uses existing route

Logout has no confirmation dialog

Logout has no API request

encrypted auth state is removed

session is cleared

authentication state is reset

navigation returns to auth flow

UI reference compliance

Profile follows the provided section structure

tile styling follows the provided Profile screenshots

Language bottom sheet is used as the reference for Card Style bottom sheet

arbitrary one-off colors are not introduced

existing Material 3 theme tokens/constants are reused