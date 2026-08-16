# Wishlist Feature — Android Implementation Plan (Baltazar)

**v2 — revised against actual codebase.** Correction to v1: the data layer is **already fully implemented** (`WishlistApiService`, `WishlistRemoteDataSource`, `WishlistRepository`, `IWishlistRepository`, `WishlistNetworkModule`, `WishlistRepositoryModule` all exist and are wired into Hilt). v1 incorrectly assumed only `POST` existed and treated the feature as blocked — that was based on incomplete Swagger info at the time. What's actually missing is the **UI** and a couple of confirmations.

This file is self-contained.

---

## 1. What already exists (reuse, don't recreate)

```kotlin
// core/data/datasources/remote/services/WishlistApiService.kt — already correct
interface WishlistApiService {
    @GET("api/user/wishlist")
    suspend fun getWishlist(@Query("limit") limit: Int = 20, @Query("cursor") cursor: String? = null): PaginatedResponse<ServiceCardItemDto>

    @POST("api/user/wishlist")
    suspend fun addToWishlist(@Body request: AddToWishlistRequest): ApiResponse<Unit>

    @DELETE("api/user/wishlist/{id}")
    suspend fun removeFromWishlist(@Path("id") id: String): ApiResponse<Unit>
}
```

- `IWishlistRepository` / `WishlistRepository` (`core/domain/repository`, `core/data/repository`) — `getWishlist()`, `addToWishlist(serviceId, serviceType)`, `removeFromWishlist(id)`. Already bound via `WishlistRepositoryModule`.
- Uses the shared `ServiceCardItem` domain model / `ServiceCardItemDto` — the same card model Company's ITEMS section should reuse. Good, no new card type needed.
- `NavRoutes.kt` already has an empty `Wishlist` route object, ready to attach a screen to.
- `ServiceItemCard.kt` (`core/core/components`) — reuse for rendering wishlist items, consistent with the rest of the app.

**This is NOT a toggle endpoint** — `POST` adds, `DELETE /api/user/wishlist/{id}` removes. Two separate calls, matching the existing repository's two separate methods. (v1's "toggle" assumption is wrong — ignore it.)

---

## 2. What's actually missing

### 2.1 Confirm the `GET` response shape
Only the `POST` response has been confirmed via Swagger so far. The `GET /api/user/wishlist` response shape used in code (`PaginatedResponse<ServiceCardItemDto>`, i.e. `{ success, data: [...], pagination }` with each item having `id, serviceType, serviceId, title, image, price, priceSuffix, currency, rating, ratingCount, category`) is a reasonable, consistent-with-the-rest-of-the-API guess, but **run it against the real backend before shipping** — hit the endpoint in Swagger/Postman and diff the fields against `ServiceCardItemDto`.

### 2.2 `wishlistItemId` for removal
`removeFromWishlist(id)` needs the composite `wishlistItemId` (format observed: `"${serviceType}_${serviceId}"`, e.g. `RENT_A_CAR_COUDJyHq7Rycf07B32jm"`). Two ways to get it:
- **Preferred:** once `getWishlist()` is called, each `ServiceCardItemDto.id` *should* already be this composite id (confirm — the field is literally named `id` in the DTO, consistent with this). Use it directly.
- Don't reconstruct the string manually (`"${serviceType.name}_$serviceId"`) as a primary strategy — it's fragile if the backend format ever changes. Only fall back to it if `getWishlist()` hasn't been called yet in the current screen (e.g. a heart button on a card that was never loaded from the wishlist list itself).

### 2.3 Reactive "is this wishlisted" state across the app
There's no local cache/StateFlow yet tracking which items are currently wishlisted, needed to show a filled vs. outline heart on cards anywhere in the app (list screens, detail screens), not just inside the Wishlist tab itself.

**Recommended addition** — a thin session-cache layer, same shape as `SessionManager`:

```kotlin
// core/core/managers/WishlistCache.kt — NEW
@Singleton
class WishlistCache @Inject constructor() {
    private val _wishlistedIds = MutableStateFlow<Set<String>>(emptySet())
    val wishlistedIds: StateFlow<Set<String>> = _wishlistedIds.asStateFlow()

    fun hydrate(ids: List<String>) { _wishlistedIds.value = ids.toSet() }
    fun add(id: String) { _wishlistedIds.update { it + id } }
    fun remove(id: String) { _wishlistedIds.update { it - id } }
    fun clear() { _wishlistedIds.value = emptySet() }
}
```

Hydrate it once from `WishlistScreen`'s first `getWishlist()` load (or, if you want hearts correct app-wide from cold start, hydrate it alongside `SplashViewModel.checkStatus()` right after the user session is confirmed — mirrors how `SessionManager` is populated once at splash). Call `WishlistCache.clear()` on logout, same as `SessionManager.clear()`.

### 2.4 `WishlistButton` component (`core/core/components/WishlistButton.kt`) — NEW

```kotlin
@Composable
fun WishlistButton(
    serviceId: String,
    serviceType: ServiceType,
    modifier: Modifier = Modifier,
    viewModel: WishlistButtonViewModel = hiltViewModel()
) {
    val wishlistedIds by viewModel.wishlistedIds.collectAsStateWithLifecycle()
    val compositeId = "${serviceType.name}_$serviceId"
    val isWishlisted = compositeId in wishlistedIds

    IconButton(onClick = { viewModel.toggle(serviceId, serviceType, compositeId, isWishlisted) }, modifier = modifier) {
        Icon(
            imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = if (isWishlisted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@HiltViewModel
class WishlistButtonViewModel @Inject constructor(
    private val wishlistRepository: IWishlistRepository,
    private val wishlistCache: WishlistCache
) : ViewModel() {
    val wishlistedIds: StateFlow<Set<String>> = wishlistCache.wishlistedIds

    fun toggle(serviceId: String, serviceType: ServiceType, compositeId: String, currentlyWishlisted: Boolean) {
        viewModelScope.launch {
            val result = if (currentlyWishlisted) {
                wishlistRepository.removeFromWishlist(compositeId)
            } else {
                wishlistRepository.addToWishlist(serviceId, serviceType)
            }
            result.onSuccess {
                if (currentlyWishlisted) wishlistCache.remove(compositeId) else wishlistCache.add(compositeId)
            }.onFailure { /* surface via AppSnackbar, already set up in MainActivity */ }
        }
    }
}
```

Requires auth (Bearer) — if the current `SessionManager.user.role == "GUEST"`, redirect to `Login` instead of calling the repository.

### 2.5 `WishlistScreen` — NEW

Attach to the existing empty `Wishlist` route. Standard `Scaffold` + `LazyColumn` of `ServiceItemCard` fed by `getWishlist()`, same loading/empty/error pattern as everywhere else in the app. On load, call `wishlistCache.hydrate(result.map { it.id })` so hearts across the rest of the app immediately reflect reality.

### 2.6 Bottom nav wiring
`BottomNavBar.kt` is currently **entirely commented out** — the `Wishlist`/`Explore`/`Profile` tab bar isn't active yet. Confirm whether this is intentional (still WIP) before assuming users can reach the Wishlist tab at all; if not, `WishlistScreen` needs another entry point for now (e.g. from a service detail screen's heart icon deep link).

### 2.7 Dead-code check
`WishlistItemDto.kt` / `WishlistResponseDto.kt` / `AddToWishlistDto.kt` and the domain types `WishlistItem` / `WishlistPage` / `AddToWishlistInfo` exist in the codebase but are **not referenced** by `WishlistRepository`/`WishlistRemoteDataSource`/`WishlistApiService` (which use `ServiceCardItemDto` / `AddToWishlistRequest` instead). Looks like leftovers from an earlier iteration. Confirm with the rest of the team before deleting — don't remove them as part of this feature work without checking nothing else references them.

---

## 3. Acceptance checklist

- [ ] `GET /api/user/wishlist` response shape verified against real Swagger, not just assumed
- [ ] Wishlist screen loads, paginates, shows loading/empty/error states
- [ ] Heart icon reflects true wishlist state after `getWishlist()` hydration, not just current-session toggles
- [ ] Add uses `POST`, remove uses `DELETE {id}` — never a toggle call
- [ ] `WishlistButton` redirects to login when `SessionManager.user` is guest
- [ ] `WishlistCache` cleared on logout
- [ ] Confirmed whether `WishlistItemDto`/`AddToWishlistDto`/etc. are dead code before touching them
PLANEOF
echo "wishlist plan rewritten: $(wc -l < /mnt/user-data/outputs/wishlist-implementation-plan.md) lines"