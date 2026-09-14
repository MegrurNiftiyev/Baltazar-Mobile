# Project Refactoring Plan (Baltazar Mobile) - Updated

Bu sənəd layihədə həyata keçirilən refaktorinq, dizayn, lokalizasiya, `isLiked` (Wishlist) inteqrasiyası və Edge-to-Edge yenilənmələrini ehtiva edir.

---

## 1. Edge-to-Edge Və Status Bar Window Inset Handling
- **Xüsusiyyət:**
  - Yalnız servis detalları (`TravelDetail`, `CarDetail`, `FoodDetail`, `HotelDetail`, `CompanyDetail`) və `TourRoadmapScreen` (xəritə ekranı) üçün şəkil/xəritə ən üstə (status bar altına) qədər uzanacaq (Edge-to-Edge zero top padding). Oradakı floating düymələr (Back, Favorite) `windowInsetsPadding(WindowInsets.statusBars)` istifadə edəcək.
  - Digər bütün ekranlarda kontentin status bar və ya naviqasiya paneli altında qalmaması üçün standart `WindowInsets` padding-ləri tətbiq olunacaq.

---

## 2. Detal Səhifələrində Bütün Mətnlərin Lokalizasiyası (Strings.xml)
- **Xüsusiyyət:**
  - Detal səhifələrindəki bütün statik yazılar ("Xüsusiyyətlər", "Rəylər", "Haqqında", "Tərkibi", "Rezerv et", "İştirak et", "Sifariş et", "Qiymət", "Paket qiyməti" və s.) `core/src/main/res/values/strings.xml` (və `values-az`, `values-en`, `values-tr`) resurslarına çıxarılacaq.
  - Kod daxilində bütün hardcoded string-lər `stringResource(R.string...)` ilə əvəz ediləcək.

---

## 3. DTO və Domain Modellərinə `isLiked: Boolean` Sahəsinin Əlavəsi və Wishlist İnteqrasiyası
- **Backend Uyğunluğu:**
  - `ServiceCardItemDto`, `ServiceCardItem`
  - `RelatedItemDto`, `RelatedItem`
  - `HotelDto`, `HotelDetailDto`, `HotelItem`, `HotelDetail`
  - `CarDto`, `CarDetailDto`, `CarItem`, `CarDetail`
  - `TourItemDto`, `TourDetailDto`, `TourItem`, `TourDetail`
  - `FoodItemDto`, `FoodDetailDto`, `FoodItem`, `FoodDetail`
  - `WishlistItemDto` (`isLiked = true`)
- **Wishlist Düymələri:**
  - Üzərində bəyənmə (FavoriteButton) olan bütün kartlarda və detal səhifələrində `IWishlistRepository` inteqrasiya ediləcək. Düyməyə basıldıqda serverdə wishlist sorğusu icra olunacaq və UI state `isLiked` yenilənəcək.

---

## 4. Xəritə Ekranında Shimmer və Xəritəyə Keçid Düzəlişi
- **Xəritə Xətası Dialoqunun Ləğvi:**
  - Travel Detal və Tour Roadmap ekranlarında "Google Map API açarı tapılmadı" dialoqu / xəbərdarlıq mətni tamamilə yığışdırılacaq. Xəritə yüklənənə kimi yerinə səliqəli `ShimmerWrapper` göstəriləcək.
- **Xəritəyə Kliklə Keçid:**
  - Travel Detal səhifəsində həm "Xəritədə bax" başlığına, həm də birbaşa xəritə komponentinin özünə kliklədikdə xəritə ekranına (`TourRoadmapScreen`) keçid təmin ediləcək.

---
