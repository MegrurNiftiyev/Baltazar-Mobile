# 🚀 Baltazar Task List

## ✅ Tamamlananlar
- [x] **AuthSelection Screen**: UI, ViewModel və State strukturu quruldu (Login, Register, Guest seçimləri).
- [x] **Localization**: Bütün auth ekranları (Onboarding, Selection, Login, Register) EN, AZ, TR dillərinə tam uyğunlaşdırıldı.
- [x] **DI Standardization**: Bütün modullarda DI qovluqları `core/di` olaraq standartlaşdırıldı.
- [x] **Font & Style**: Inter şrift ailəsi layihəyə inteqrasiya edildi və Typography yeniləndi.
- [x] **Project SDK**: Bütün modullarda `minSdk` 25-ə qaldırıldı.
- [x] **Company Components Refactoring**: `Company` modulundakı komponentlər sadələşdirildi (`Company` prefiksi silindi), dizayn tokenləri və lokallaşdırma təmin edildi.
- [x] **Company List Service Navigation**: Servis ekranlarından (`Travels`, `RentACars`, `Foods`, `Hotels`) müvafiq servisin `CompanyList`-inə keçid düyməsi əlavə edildi.

## 🚧 Növbəti Addımlar (Sabah Davam Ediləcəklər)

### 1. Hər Modulun NavGraph-ına Şirkətlər Marşrutunun Qoşulması
- [ ] `travelNavGraph`, `rentACarNavGraph`, `foodNavGraph`, `hotelNavGraph` daxilində müvafiq servisin şirkətlər siyahısı və detail keçidlərinin (`composable<TravelCompanies>`, `composable<TravelCompanyDetail>` və s.) birbaşa `:feature:company` ekranlarına bağlanması.

### 2. List Və Detail Ekranlarında Refresh İndikatorlarının Düzəldilməsi
- [ ] Servislərin siyahı ekranlarında və detail ekranlarında `PullToRefresh` (yenilənmə indikatoru) işləməsinin auditi və indikatorun düzgün göstərilməsinin təmin edilməsi.

### 3. Detail Ekranlarının Karusel Və Şəkil Overlay Yenilənməsi (`DetailTopImageCarousel` / `DetailBox`)
- [ ] **Karusel Nöqtələri (Dots):** Karusel indikator nöqtələrinin ölçüləri kiçildilməli və daha səliqəli, mütənasib görünüşə gətirilməlidir.
- [ ] **Konteynin Küncləri Və Üst-Üstə Düşməsi (Border Radius & Overlap):** Detail box-un border radius-u 2 qat artırılmalı (`32.dp` / `BorderRadiuses.ExtraLarge`) və konteyner bir qədər yuxarı qaldırılaraq örtük şəkillərinin üstünə səliqəli şəkildə keçirilməlidir (`overlap/offset`).

### 4. Rəy Və Reytinq Yazma Komponenti (`ReviewSubmissionComponent`)
- [ ] **Yeni Komponent:** Rəy yazmaq icazəsi olan yerlərdə (istifadəçi sifariş verdikdən / icazə verildikdən sonra) həm `CompanyDetail`, həm də digər servis detail ekranları üçün ulduzlu reytinq vermə + rəy yazma komponenti/dialoqu hazırlanmalıdır.

### 5. Favori (Ürək) Düyməsinin Yenilənməsi (`StandardItemCard` / `FavoriteButton`)
- [ ] **İkon Dəyişikliyi:** Favori olunduqda içi dolu ürək ikonu (`IconHeartFilled` / `HeartFilled`), olmadıqda isə kontur ikon istifadə edilməli.
- [ ] **Rəng Düzəlişi:** Detail ekranındakı kimi favori olunduqda qırmızı rəng (`MaterialTheme.colorScheme.error`) ilə seçilməli.

### 6. Food Detail Ekranının Bədcləri Və Xüsusi Rəngləri (`FoodDetailScreen`)
- [ ] Kalori, zülal, karbohidrat və yağ göstəriciləri üçün xüsusi göstərici rənglərinin (qırmızı, sarı, yaşıl və s.) bərpası.

### 7. Detail Ekranlarının Lokallaşdırılması (`strings.xml`)
- [ ] Bütün Detail ekranlarındakı ("Haqqında", "Rəylər", "Tərkibi", "Xüsusiyyətlər" və s.) sabit statik mətnlərin auditi və `:core` `strings.xml` resursuna köçürülməsi.
