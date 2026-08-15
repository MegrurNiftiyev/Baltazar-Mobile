# Company Feature — Android Implementation Plan (Baltazar)

**v2 — revised against actual codebase (`merged__40_/41_/42_.txt`).** Follows the exact `IXRepository / XRepositoryImpl / XRemoteDataSource / XApiService / XNetworkModule / XRepositoryModule` pattern already used by `User`, `Wishlist`, and `Explore` in this project. Lives in `:core`, base package `com.example.baltazar.core`.

This file is self-contained.

---

## 1. Scope

`serviceType` applies to: `RENT_A_CAR`, `TRAVEL`, `FOOD`. **Never** `HOTEL` (standalone, not company-tied).
Deferred: `related-items` endpoint (explicit `TODO`, per earlier decision).

---

## 2. What already exists vs. what's new

**Already exist (reuse, do not recreate):**
- `ApiResponse<T>` / `PaginatedResponse<T>` (`core/data/model/response`) — use these directly, don't invent new response wrapper DTOs.
- `PaginationDto` → `PaginationInfo` mapping.
- `ServiceType` enum (`@Serializable`, has `UNKNOWN`).
- `CompanyListItemDto.kt`, `CompanyDetailsDto.kt` (`core/data/model/dto`) — **exist but are stubs that need fixing, see Section 3.**
- `CompanyStatus.kt` (`core/core/enums`) — **exists but needs fixing, see Section 3.**
- `ServiceItemCard.kt` (`core/core/components`) — reuse for rendering items.
- `NavRoutes.kt` — centralized, single file for **all** app routes. Add Company routes here, don't create a separate route file.

**New (this plan builds them):**
- `CompanySection.kt` enum — doesn't exist yet.
- `ReviewEligibilityDto` — doesn't exist yet.
- Domain models `Company`, `CompanyDetail` — don't exist yet.
- `ICompanyRepository`, `CompanyRepositoryImpl`, `CompanyRemoteDataSource`, `CompanyApiService`, `CompanyNetworkModule`, `CompanyRepositoryModule`.
- `CompanyListScreen`, `CompanyDetailScreen` + ViewModels.

---

## 3. Fix the two existing DTOs first

Current `CompanyListItemDto.kt` and `CompanyDetailsDto.kt` type `serviceType`/`status` as raw `String`, and are missing fields the real API actually returns (`sectionOrder` on the list item, `reviewEligibility` on the detail). They also carry speculative fields (`images`, `address`) that **do not appear in the confirmed Swagger response** — keep them but mark nullable/unconfirmed.

```kotlin
// core/core/enums/CompanyStatus.kt — FIX: add @Serializable + UNKNOWN fallback
@Serializable
enum class CompanyStatus {
    @SerialName("ACTIVE") ACTIVE,
    @SerialName("INACTIVE") INACTIVE,
    @SerialName("UNKNOWN") UNKNOWN
}
```

```kotlin
// core/core/enums/CompanySection.kt — NEW
@Serializable
enum class CompanySection {
    @SerialName("HEADER") HEADER,
    @SerialName("ABOUT") ABOUT,
    @SerialName("GALLERY") GALLERY,
    @SerialName("ITEMS") ITEMS,
    @SerialName("REVIEWS") REVIEWS,
    @SerialName("UNKNOWN") UNKNOWN
}
```

```kotlin
// core/data/model/dto/CompanyListItemDto.kt — FIX
@Serializable
data class CompanyListItemDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("about") val about: String? = null,
    @SerialName("serviceType") val serviceType: ServiceType,       // was String
    @SerialName("logo") val logo: String? = null,
    @SerialName("profileImage") val profileImage: String? = null,
    @SerialName("bannerImage") val bannerImage: String? = null,
    @SerialName("status") val status: CompanyStatus = CompanyStatus.UNKNOWN, // was String?
    @SerialName("sectionOrder") val sectionOrder: List<CompanySection> = emptyList(), // NEW — confirmed present in real /api/companies response
    @SerialName("rating") val rating: Double? = null,
    @SerialName("reviewCount") val reviewCount: Int? = null,
    @SerialName("cuisineTypes") val cuisineTypes: List<String>? = null // FOOD only
)
```

```kotlin
// core/data/model/dto/CompanyDetailsDto.kt — FIX
@Serializable
data class CompanyDetailsDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("about") val about: String? = null,
    @SerialName("serviceType") val serviceType: ServiceType,        // was String
    @SerialName("logo") val logo: String? = null,
    @SerialName("profileImage") val profileImage: String? = null,
    @SerialName("bannerImage") val bannerImage: String? = null,
    @SerialName("images") val images: List<String> = emptyList(),   // ⚠️ NOT in confirmed Swagger response — confirm with backend before relying on it for GALLERY
    @SerialName("address") val address: String? = null,             // ⚠️ same — not in confirmed response
    @SerialName("status") val status: CompanyStatus = CompanyStatus.UNKNOWN, // was String?
    @SerialName("rating") val rating: Double? = null,
    @SerialName("reviewCount") val reviewCount: Int? = null,
    @SerialName("fullSectionOrder") val fullSectionOrder: List<CompanySection> = emptyList(), // was List<String>
    @SerialName("cuisineTypes") val cuisineTypes: List<String>? = null,
    @SerialName("reviewEligibility") val reviewEligibility: ReviewEligibilityDto? = null // NEW — was missing entirely
)

@Serializable
data class ReviewEligibilityDto(
    @SerialName("eligible") val eligible: Boolean = false,
    @SerialName("alreadyReviewed") val alreadyReviewed: Boolean = false,
    @SerialName("canSubmit") val canSubmit: Boolean = false
)
```

**Open question:** confirm with backend whether `images` (gallery) and `address` are real fields planned for the detail response — the confirmed Swagger sample doesn't include them, only `profileImage`/`bannerImage`.

---

## 4. Domain models (`core/domain/model`)

```kotlin
data class Company(
    val id: String,
    val name: String,
    val about: String?,
    val serviceType: ServiceType,
    val logo: String?,
    val profileImage: String?,
    val bannerImage: String?,
    val status: CompanyStatus,
    val sectionOrder: List<CompanySection>,
    val rating: Double,
    val reviewCount: Int,
    val cuisineTypes: List<String>?
)

data class CompanyDetail(
    val company: Company,
    val images: List<String>,
    val address: String?,
    val fullSectionOrder: List<CompanySection>,
    val reviewEligibility: ReviewEligibility
)

data class ReviewEligibility(
    val eligible: Boolean,
    val alreadyReviewed: Boolean,
    val canSubmit: Boolean
)
```

Add `toDomain()` mappers on the DTOs, same as every existing DTO in the project (`CompanyListItemDto.toDomain()`, `CompanyDetailsDto.toDomain()`).

---

## 5. Data layer — mirrors `UserRepositoryImpl` / `WishlistRepository` exactly

```kotlin
// core/data/datasources/remote/services/CompanyApiService.kt
interface CompanyApiService {
    @GET("api/companies")
    suspend fun getCompanies(
        @Query("serviceType") serviceType: String,
        @Query("limit") limit: Int = 20,
        @Query("cursor") cursor: String? = null
    ): PaginatedResponse<CompanyListItemDto>

    @GET("api/companies/{id}")
    suspend fun getCompanyDetails(@Path("id") id: String): ApiResponse<CompanyDetailsDto>
}
```

```kotlin
// core/data/datasources/remote/CompanyRemoteDataSource.kt
class CompanyRemoteDataSource @Inject constructor(
    private val apiService: CompanyApiService
) {
    suspend fun getCompanies(serviceType: String, limit: Int, cursor: String?) =
        apiService.getCompanies(serviceType, limit, cursor)

    suspend fun getCompanyDetails(id: String) =
        apiService.getCompanyDetails(id)
}
```

```kotlin
// core/domain/repository/ICompanyRepository.kt
interface ICompanyRepository {
    suspend fun getCompanies(
        serviceType: ServiceType,
        cursor: String? = null,
        limit: Int = 20
    ): Result<PaginatedList<Company>>

    suspend fun getCompanyDetails(companyId: String): Result<CompanyDetail>
}
```

```kotlin
// core/data/repository/CompanyRepositoryImpl.kt — same try/catch + success/data-check style as UserRepositoryImpl, NOT runCatching
@Singleton
class CompanyRepositoryImpl @Inject constructor(
    private val remoteDataSource: CompanyRemoteDataSource
) : ICompanyRepository {

    override suspend fun getCompanies(
        serviceType: ServiceType,
        cursor: String?,
        limit: Int
    ): Result<PaginatedList<Company>> {
        return try {
            val response = remoteDataSource.getCompanies(serviceType.name, limit, cursor)
            if (response.success) {
                val companies = response.data.map { it.toDomain() }
                val pagination = response.pagination?.toDomain()
                    ?: PaginationInfo(nextCursor = null, hasMore = false, limit = limit)
                Result.success(PaginatedList(items = companies, pagination = pagination))
            } else {
                Result.failure(Exception("Failed to fetch companies"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCompanyDetails(companyId: String): Result<CompanyDetail> {
        return try {
            val response = remoteDataSource.getCompanyDetails(companyId)
            val data = response.data
            if (response.success && data != null) {
                Result.success(data.toDomain())
            } else {
                Result.failure(Exception("Failed to fetch company details"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

```kotlin
// core/core/di/CompanyNetworkModule.kt
@Module
@InstallIn(SingletonComponent::class)
object CompanyNetworkModule {
    @Provides
    @Singleton
    fun provideCompanyApiService(@Named("AppRetrofit") retrofit: Retrofit): CompanyApiService =
        retrofit.create(CompanyApiService::class.java)
}
```

```kotlin
// core/core/di/CompanyRepositoryModule.kt
@Module
@InstallIn(SingletonComponent::class)
abstract class CompanyRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCompanyRepository(impl: CompanyRepositoryImpl): ICompanyRepository
}
```

(`PaginatedList<T>` and `PaginationInfo` already exist in `core/domain/model` — reuse them, don't reinvent.)

---

## 6. Navigation — add to the existing `NavRoutes.kt` (single centralized file)

```kotlin
@Serializable
data class CompanyList(val serviceType: ServiceType)

@Serializable
data class CompanyDetail(val companyId: String, val serviceType: ServiceType)
```

⚠️ `NavRoutes.kt` currently already has one-off `FoodCompanyList` / `FoodCompanyDetail(id)` objects that were never wired to a screen. Recommend **removing them** and replacing with the generic pair above (confirm nothing already references them before deleting). Register once in `AppNavGraph` (or wherever `:core` screens go), called from `:feature:rentacar`, `:feature:travel`, `:feature:food` with `CompanyList(ServiceType.RENT_A_CAR)` etc.

---

## 7. Screens

Same `HiltViewModel` + `SavedStateHandle.toRoute<CompanyList>()` pattern as the rest of the app.

```kotlin
@HiltViewModel
class CompanyListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val companyRepository: ICompanyRepository
) : ViewModel() {
    private val route = savedStateHandle.toRoute<CompanyList>()
    private val _uiState = MutableStateFlow(CompanyListUiState(isLoading = true))
    val uiState: StateFlow<CompanyListUiState> = _uiState.asStateFlow()

    init { load() }

    fun load(cursor: String? = null) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = cursor == null, isLoadingMore = cursor != null) }
        companyRepository.getCompanies(route.serviceType, cursor)
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        companies = if (cursor == null) result.items else it.companies + result.items,
                        pagination = result.pagination,
                        isLoading = false, isLoadingMore = false, error = null
                    )
                }
            }
            .onFailure { e -> _uiState.update { it.copy(isLoading = false, isLoadingMore = false, error = e.message) } }
    }
}

data class CompanyListUiState(
    val companies: List<Company> = emptyList(),
    val pagination: PaginationInfo? = null,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null
)
```

`CompanyListScreen` and `CompanyDetailScreen` composables: same structure as previously drafted (`Scaffold` + `TopAppBar` + loading/error/empty states + `LazyColumn`), rendering `CompanyCard` (new, reuse look of `ServiceItemCard.kt`) and, in detail, iterating `fullSectionOrder` to render `HEADER/ABOUT/GALLERY/ITEMS/REVIEWS`, skipping `UNKNOWN`.

---

## 8. ITEMS section — per-service-type fetching

Same recommendation as before: Hilt map-multibinding (`Map<ServiceType, Provider<CompanyItemsProvider>>`), each feature module (`:feature:rentacar`, `:feature:travel`, `:feature:food`) contributes its own `@IntoMap @ServiceTypeKey(...)` binding, mapping its own item DTO to the existing `ServiceCardItem` domain model (already used by Wishlist — reuse it here too, don't invent a new card model), rendered via the existing `ServiceItemCard.kt`.

```
FOOD       GET /api/services/food/items?companyId={companyId}
RENT_A_CAR GET /api/services/rentacar/cars?companyId={companyId}
TRAVEL     GET /api/services/travel/tours?companyId={companyId}
```

---

## 9. Reviews section

```
GET /api/reviews?targetType=COMPANY&targetId={companyId}&limit=20&cursor=<cursor>
```
New `ReviewDto`/`ReviewApiService` following the same 4-file pattern. Gate the "write a review" action on `reviewEligibility.canSubmit`.

---

## 10. Open questions

- `images` (gallery) and `address` fields on `CompanyDetailsDto` are not in the confirmed Swagger sample — confirm before shipping the GALLERY section.
- `CompanyStatus` — only `ACTIVE`/`INACTIVE` observed; confirm if more values exist.
- Whether `FoodCompanyList`/`FoodCompanyDetail` in `NavRoutes.kt` are safe to delete (unreferenced elsewhere?).

## 11. Acceptance checklist

- [ ] `CompanyListItemDto`/`CompanyDetailsDto` use `ServiceType`/`CompanyStatus` enums directly, not raw strings
- [ ] List/detail never called with `serviceType = HOTEL`
- [ ] Unknown `CompanyStatus`/`CompanySection`/`ServiceType` values fall back to `UNKNOWN` without crashing
- [ ] Detail sections render strictly per `fullSectionOrder`
- [ ] ITEMS section reuses `ServiceCardItem` + `ServiceItemCard.kt`
- [ ] `related-items` left as `TODO`, not implemented
- [ ] New DI modules registered and app compiles/injects correctly (`CompanyNetworkModule`, `CompanyRepositoryModule`)
PLANEOF
echo "company plan rewritten: $(wc -l < /mnt/user-data/outputs/company-implementation-plan.md) lines"