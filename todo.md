Implementation Plan: UI Polish, Edge-to-Edge, Company Redesign & Localization
Comprehensive plan to address currency dynamic resolution, full strings localization for detail sections, overlapping container border radius doubling, custom favorite color definition, real API price & suffix wiring, EmptyStateView reusable component for Wishlist & Orders, nutrition chart dedicated macro colors, card price/suffix overflow safety, outer Scaffold edge-to-edge in MainActivity, Hotel screen company button removal, and Company List & Detail screen redesign.

Proposed Changes
1. Core Module (:core)
[NEW] 
EmptyStateView.kt
Create a modern, reusable empty state composable featuring:
Icon with soft circular container background
Headline/Title with MaterialTheme.typography.titleMedium
Subtitle/Description with MaterialTheme.typography.bodyMedium
Optional action button (Button / OutlinedButton)
Centered layout suitable for WishlistScreen, OrdersScreen, and generic empty lists.
[MODIFY] 
Color.kt
Define custom dedicated colors:
FavoriteRed = Color(0xFFFF3355) (Vibrant modern heart rose/red)
NutritionCalories = Color(0xFFF59E0B) (Amber / Warm Orange)
NutritionProtein = Color(0xFFEF4444) (Coral Red)
NutritionFat = Color(0xFF8B5CF6) (Purple)
NutritionCarbs = Color(0xFF06B6D4) (Cyan / Aqua Blue)
[MODIFY] 
FavoriteButton.kt
 & 
DetailTopBarOverlay.kt
Update default active/selected tint from MaterialTheme.colorScheme.error to FavoriteRed.
[MODIFY] 
StandardItemCard.kt
 & 
PriceExtensions.kt
Enhance price + suffix layout to prevent suffix overflow or awkward breaking when currency/suffix strings are long.
Use FavoriteButton with FavoriteRed for active state and Color.White for default unselected state.
[MODIFY] String Resources
Update 
strings.xml
, values-az/strings.xml, values-en/strings.xml, values-tr/strings.xml to include all missing strings for detail screen headers, empty states (wishlist_empty_title, wishlist_empty_subtitle, orders_empty_title, orders_empty_subtitle, etc.).
2. App Module (:app)
[MODIFY] 
MainActivity.kt
Update outer Scaffold to have contentWindowInsets = WindowInsets(0, 0, 0, 0) so it does not consume system insets, and remove Modifier.padding(innerPadding) from AppNavGraph. Each internal screen manages its own insets.
3. Detail Screens (Border Radius, Currency & Localization)
[MODIFY] 
FoodDetailScreen.kt
Increase top corner border radius of the overlapping card from BorderRadiuses.ExtraLarge (24.dp) to BorderRadiuses.Massive (48.dp).
Pass dynamic currency and price suffix.
Ensure all headers & texts are localized.
[MODIFY] 
NutritionChart.kt
Use custom colors NutritionCalories, NutritionProtein, NutritionFat, NutritionCarbs instead of theme palette defaults.
[MODIFY] 
CarDetailScreen.kt
Increase top corner border radius of overlapping card to BorderRadiuses.Massive (48.dp).
Remove legacy SideEffect status bar color modification block.
Pass dynamic currency and suffix.
[MODIFY] 
TravelDetailScreen.kt
Increase top corner border radius of overlapping card to BorderRadiuses.Massive (48.dp).
Remove legacy SideEffect status bar color modification block.
Pass dynamic currency and suffix.
[MODIFY] 
HotelDetailScreen.kt
Increase top corner border radius of overlapping card to BorderRadiuses.Massive (48.dp).
Remove legacy SideEffect status bar color modification block.
Pass dynamic currency and suffix.
4. Feature Modules (Hotels, Wishlist, Orders, Company)
[MODIFY] 
HotelsScreen.kt
Remove trailingContent (Company list icon button) from CustomAppBar in HotelsScreen.
[MODIFY] 
WishlistScreen.kt
Integrate EmptyStateView when wishlist is empty with icon TablerIcons.Heart, localized title, and subtitle.
[MODIFY] 
OrdersScreen.kt
Integrate EmptyStateView when orders list is empty with icon TablerIcons.Receipt or TablerIcons.Package, localized title, and subtitle.
[MODIFY] 
CompanyCard.kt
Redesign into a clean horizontal Row card:
Left: Rounded company logo/avatar (e.g. 56x56dp or 64x64dp) with fallback placeholder.
Middle: Company name, category chip/pill, address with pin icon.
Right: Rating badge / arrow indicator.
[MODIFY] 
CompanyDetailScreen.kt
Redesign top bar & header:
Replace DetailTopImageCarousel and DetailTopBarOverlay with standard CustomAppBar (Title = company name / header, back button).
No wishlist / favorite toggle.
Create attractive company profile header (cover / avatar banner, verification badge, contact actions).
Verification Plan
Automated Build Verification
Run ./gradlew :app:assembleDebug --daemon to verify all modules compile cleanly with zero errors.
Manual Verification Flow
Verify MainActivity edge-to-edge behavior across screens.
Open FoodDetailScreen, CarDetailScreen, TravelDetailScreen, HotelDetailScreen and check:
Overlapping card top border radius is 48.dp (double).
Dynamic price currency & suffix.
Localized headers and text.
Heart button displays custom vibrant red when favorited.
Open HotelsScreen and verify no Company button in TopBar.
Open WishlistScreen and OrdersScreen and verify empty state UI with EmptyStateView.
Open CompanyListScreen and CompanyDetailScreen and verify row-based card and custom header design without carousel / favorite icon.

bunalri deysdin 

Color.kt
EmptyStateView.kt
FavoriteButton.kt
DetailTopBarOverlay.kt
DetailTopBarOverlay.kt
DetailTopBarOverlay.kt
strings.xml
strings.xml
strings.xml
strings.xml
MainActivity.kt
MainActivity.kt
MainActivity.kt
FoodDetailDto.kt
FoodDetailDto.kt
FoodDetail.kt
FoodDetailScreen.kt
NutritionChart.kt
NutritionChart.kt
NutritionChart.kt
CarDetailDto.kt
CarDetail.kt
CarDetailScreen.kt
CarDetailScreen.kt
TourDetailDto.kt
TourDetail.kt
TravelDetailScreen.kt#L95-165
Working