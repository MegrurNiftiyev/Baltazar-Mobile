# 🚀 Baltazar Auth Task List

## ✅ Tamamlananlar
- [x] **AuthSelection Screen**: UI, ViewModel və State strukturu quruldu (Login, Register, Guest seçimləri).
- [x] **Localization**: Bütün auth ekranları (Onboarding, Selection, Login, Register) EN, AZ, TR dillərinə tam uyğunlaşdırıldı.
- [x] **DI Standardization**: Bütün modullarda DI qovluqları `core/di` olaraq standartlaşdırıldı.
- [x] **Font & Style**: Inter şrift ailəsi layihəyə inteqrasiya edildi və Typography yeniləndi.
- [x] **Project SDK**: Bütün modullarda `minSdk` 25-ə qaldırıldı.

## 🚧 Növbəti Addımlar (Səhər Davam Ediləcəklər)

### 1. Repository Bağlantıları (DI)
- [ ] `AuthRepository` daxilindəki funksiyaların (login, register, google login) real məntiqlərini yazmaq.
- [ ] Yaradılmış `RepositoryModule`-un bütün auth ViewModel-lər tərəfindən düzgün istifadə edildiyindən əmin olmaq.

### 2. UI & Navigation Refinement
- [ ] Login ekranındakı Google düyməsinə müvafiq ikonun əlavə edilməsi.
- [ ] AuthSelection ekranından Login və Register-ə keçidlərin backstack idarəetməsini yoxlamaq.

### 3. Integration & Merge
- [ ] `feature/auth` budağındakı dəyişiklikləri test etmək.
- [ ] Hər şey qaydasında olduqda `develop` və ya `main` budağına **Merge** etmək.

### 4. Xırda Düzəlişlər
- [ ] `TODO.md`-də qeyd olunan digər xırda UI detalları (məsələn, login ekranındakı şifrə sahəsinin ikonu).
