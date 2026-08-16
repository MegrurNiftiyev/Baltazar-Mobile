# 🚀 Baltazar Detailed Task & Refactoring Specifications

## ✅ Tamamlananlar
- [x] **AuthSelection Screen**: UI, ViewModel və State strukturu quruldu.
- [x] **Localization**: Bütün auth ekranları (Onboarding, Selection, Login, Register) EN, AZ, TR dillərinə tam uyğunlaşdırıldı.
- [x] **DI Standardization**: Bütün modullarda DI qovluqları `core/di` olaraq standartlaşdırıldı.
- [x] **Font & Style**: Inter şrift ailəsi layihəyə inteqrasiya edildi və Typography yeniləndi.
- [x] **Project SDK**: Bütün modullarda `minSdk` 25-ə qaldırıldı.
- [x] **Company Components Refactoring**: `Company` modulundakı komponentlər sadələşdirildi (`Company` prefiksi silindi), dizayn tokenləri və lokallaşdırma təmin edildi.
- [x] **Company List Service Navigation**: Servis ekranlarından (`Travels`, `RentACars`, `Foods`, `Hotels`) müvafiq servisin `CompanyList`-inə keçid düyməsi əlavə edildi.

---

## 🚧 Növbəti Addımlar (Sabah Görüləcək Detallı İşlər)

### 1. Hər Modulun NavGraph-ında Şirkətlər Marşrutunun İnteqrasiyası
- [ ] **Məqsəd:** Hər modulun öz daxili navqasiya qrafında (`travelNavGraph`, `rentACarNavGraph`, `foodNavGraph`, `hotelNavGraph`) müvafiq servisin şirkətlər siyahısı və detail marşrutları yaradılacaq.
- [ ] **Nümunə Kod Strukturu:**
  ```kotlin
  // Example for feature:travel
  @Serializable object TravelCompanies
  @Serializable data class TravelCompanyDetail(val id: String)

  fun NavGraphBuilder.travelNavGraph(navController: NavHostController) {
      composable<TravelsList> { ... }
      composable<TravelCompanies> {
          CompanyListScreen(
              navController = navController,
              serviceType = ServiceType.TRAVEL,
              onCompanyClick = { id -> navController.navigate(TravelCompanyDetail(id)) },
              onBackClick = { navController.popBackStack() }
          )
      }
      composable<TravelCompanyDetail> { backStackEntry ->
          val args = backStackEntry.toRoute<TravelCompanyDetail>()
          CompanyDetailScreen(navController = navController, companyId = args.id)
      }
  }
  ```
- [ ] Analoji olaraq `rentACarNavGraph`, `foodNavGraph` və `hotelNavGraph` modullarında eyni marşrutlar qoşulacaq.

---

### 2. List Və Detail Ekranlarında Refresh İndikatorlarının Və State-lərinin Düzəldilməsi
- [ ] **Məqsəd:** `CompanyListScreen`, `TravelsScreen`, `RentACarsScreen`, `FoodsScreen`, `HotelsScreen` və Detail ekranlarında `PullToRefreshBox` mexanizmi tam audit olunacaq.
- [ ] `state.isRefreshing` bayrağının pull-to-refresh hərəkəti zamanı `true` olması və indikatorun vizual olaraq fırlanması təmin ediləcək.
- [ ] Məlumatlar gəldikdən sonra `isRefreshing = false` edilərək indikator itiriləcək.

---

### 3. Detail Ekranlarının Karusel Və Şəkil Overlay Yenilənməsi (`DetailTopImageCarousel` / `DetailBox`)
- [ ] **Karusel Nöqtələri (Dots Indicator):**
  - Hazırkı böyük indikator nöqtələri balacalaşdırılacaq: Passiv nöqtələr `6.dp x 6.dp` dairəvi, aktiv nöqtə `16.dp` uzadılmış pill (oval) formasına gətiriləcək.
  - Nöqtələr arasında `Spaces.ExtraSmall` (4.dp) məsafə qoyulacaq.
  - İndikator bloku arxa fona yarımşəffaf pill konteyner içində yerləşdiriləcək.
- [ ] **Detail Box Küncləri Və Üst-Üstə Düşməsi (Border Radius & Overlap):**
  - Örtük şəklinin (cover image) altındakı məlumat qutusunun üst künc radiusu 2 qat artırılacaq: `RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)` (`BorderRadiuses.ExtraLarge`).
  - Məlumat qutusu mənfi vertikal ofset (`offset(y = (-24).dp)`) və ya mənfi top padding verilərək örtük şəklinin alt hissəsinin üstünə estetik şəkildə keçiriləcək (Edge-to-Edge görünüşü).

---

### 4. Rəy Və Reytinq Yazma Komponenti (`ReviewSubmissionComponent`)
- [ ] **Məqsəd:** İstifadəçinin rəy yazmaq icazəsi olduqda (`isEligibleForReview == true`) rəy və reytinq göndərə bilməsi üçün komponent hazırlanacaq.
- [ ] **Komponent Strukturu (`ReviewSubmissionComponent.kt`):**
  - **Ulduz Reytinqi Seçimi:** 1-dən 5-ə qədər interaktiv ulduz seçimi (kliklədikdə dolan `TablerIcons.StarFilled`).
  - **Mətn Sahəsi:** Çoxsətirli şərh yazma `OutlinedTextField` / `CustomTextField`.
  - **Göndər Düyməsi:** `CustomTextButton` / Primary düymə.
- [ ] Bu komponent `CompanyDetailScreen`, `TravelDetailScreen`, `RentACarDetailScreen`, `FoodDetailScreen` və `HotelDetailScreen` rəylər bölməsinin aşağısına inteqrasiya olunacaq.

---

### 5. Favori (Ürək) Düyməsinin Yenilənməsi (`StandardItemCard` / `FavoriteButton`)
- [ ] **İkon Dəyişikliyi:**
  - `isFavorite == true` olduqda: `TablerIcons.HeartFilled` (dolu ürək).
  - `isFavorite == false` olduqda: `TablerIcons.Heart` (kontur ürək).
- [ ] **Rəng Düzəlişi:**
  - `isFavorite == true` olduqda ikon və haşiyə rəngi: `MaterialTheme.colorScheme.error` (parlaq qırmızı).
  - `isFavorite == false` olduqda: `MaterialTheme.colorScheme.onSurfaceVariant`.

---

### 6. Food Detail Ekranının Bədcləri Və Xüsusi Rəngləri (`FoodDetailScreen`)
- [ ] Qida göstəriciləri (Kalori, Zülal, Karbohidrat, Yağ) üçün spesifik semantik container rəngləri təyin ediləcək:
  - **Kalori:** Qırmızımsı/Al-qırmızı ton (`MaterialTheme.colorScheme.errorContainer` / `error`).
  - **Karbohidrat:** Sarımşıl/Narıncı ton.
  - **Zülal (Protein):** Yaşılımtıl/Uğurlu ton.
  - **Yağ (Fat):** Əsas vurğu tonu (`primaryContainer`).

---

### 7. Detail Ekranlarının Lokallaşdırılması (`strings.xml`)
- [ ] Bütün detail ekranlarında hardcode olan sabit mətnlər auditi olunacaq və `:core:src:main:res:values:strings.xml` resursuna çıxarılacaq:
  - `"Haqqında"`, `"Rəylər Və Qiymətləndirmə"`, `"Tərkibi"`, `"Xüsusiyyətlər"`, `"Bənzər Təkliflər"`, `"Məhsullar / Təkliflər"`, `"Rəy yazın"` və s.
