# Kampüs Menüsü Uygulaması - Geliştirme Talimatları

## Proje Genel Bakış
Bu proje, kampüs yemekhanesi menülerini göstermek, kullanıcıların yemekleri değerlendirmesine olanak tanımak ve geçmiş menüleri görüntülemek için tasarlanmış bir Android uygulamasıdır.

---

## Teknoloji Stack
- **Dil:** Kotlin
- **UI Framework:** Jetpack Compose
- **Minimum SDK:** API 24 (Android 7.0)
- **Target SDK:** API 34
- **Build System:** Gradle (Kotlin DSL)

---

## Uygulama Mimarisi

### Ana Bileşenler
1. **Kimlik Doğrulama (Authentication)**
   - Giriş Ekranı (Login Screen)
   - Kayıt Ekranı (Register Screen)

2. **Ana Uygulama**
   - Ana Ekran (Günün Menüsü)
   - Yemek Detay ve Puanlama Ekranı
   - Geçmiş Menüler Ekranı
   - Profil Ekranı

3. **Navigasyon**
   - Bottom Navigation Bar (3 Tab)

---

## Ekran Detayları ve Gereksinimler

### 1. Giriş Ekranı (LoginScreen)
**Bileşenler:**
- Başlık: "Hoş Geldiniz"
- E-posta TextField (placeholder: "E-posta Adresiniz")
- Şifre TextField (placeholder: "Şifreniz", type: password)
- "Giriş Yap" Butonu
- "Hesabın yok mu? Kayıt Ol" Link

**İşlevsellik:**
- Form validasyonu (e-posta formatı kontrolü)
- Şifre görünürlük toggle
- Kayıt ekranına yönlendirme
- Başarılı girişte ana ekrana yönlendirme

### 2. Kayıt Ekranı (RegisterScreen)
**Bileşenler:**
- Başlık: "Hesap Oluştur"
- Ad Soyad TextField (placeholder: "Adınız Soyadınız")
- E-posta TextField (placeholder: "E-posta Adresiniz")
- Şifre TextField (placeholder: "Şifre Belirleyin", type: password)
- Şifre Tekrar TextField (placeholder: "Şifrenizi Onaylayın", type: password)
- "Kayıt Ol" Butonu
- "Zaten bir hesabın var mı? Giriş Yap" Link

**İşlevsellik:**
- Form validasyonu
- Şifre eşleşme kontrolü
- Şifre güvenlik seviyesi göstergesi (önerilen)
- Giriş ekranına dönüş
- Başarılı kayıtta ana ekrana yönlendirme

### 3. Ana Ekran (HomeScreen - Günün Menüsü)
**Bileşenler:**
- Başlık: "Günün Menüsü"
- Dinamik tarih gösterimi (format: "14 Ekim 2025, Salı")
- 4 Kategori Kartı:
  - Çorba
  - Ana Yemek
  - Yardımcı Yemek
  - Tatlı / Meyve

**İşlevsellik:**
- Her kart tıklanabilir
- Tıklandığında Yemek Detay ekranına yönlendirir
- Günlük menü verisini gösterir
- Pull-to-refresh desteği (önerilen)

### 4. Yemek Detay ve Puanlama Ekranı (RatingScreen)
**Bileşenler:**
- Başlık: "Yemeği Değerlendir"
- Yemek adı ve kategorisi
- "Puanınız:" etiketi
- Yıldız rating bileşeni (1-5 yıldız)
- "Yorumunuz" etiketi
- Yorum TextField (placeholder: "Bu yemek hakkındaki düşüncelerinizi yazın...", multiline)
- "Gönder" Butonu

**İşlevsellik:**
- Yıldız seçimi (tıklanabilir)
- Yorum alanı (opsiyonel)
- Başarılı gönderimde onay mesajı: "Değerlendirmeniz için teşekkürler!"
- Gönderim sonrası ana ekrana dönüş

### 5. Geçmiş Menüler Ekranı (HistoryScreen)
**Bileşenler:**
- Başlık: "Geçmiş Menüler"
- Açıklama: "Görüntülemek istediğiniz tarihi seçin."
- Tarih seçici (DatePicker)
- Seçilen tarihin menü listesi

**İşlevsellik:**
- Tarih seçim bileşeni
- Seçilen tarihe göre menü gösterimi
- Geçmiş veriler için boş durum (empty state) tasarımı

### 6. Profil Ekranı (ProfileScreen)
**Bileşenler:**
- Kullanıcı bilgileri (ad, e-posta)
- Ayarlar ve tercihler (önerilen)
- Çıkış yap butonu

---

## String Resources

Tüm ekran metinleri `res/values/strings.xml` dosyasında tanımlanmalıdır:

```xml
<!-- Genel -->
<string name="app_name">Kampüs Menüsü</string>
<string name="success_rating">Değerlendirmeniz için teşekkürler!</string>

<!-- Login Screen -->
<string name="login_title">Hoş Geldiniz</string>
<string name="login_email_hint">E-posta Adresiniz</string>
<string name="login_password_hint">Şifreniz</string>
<string name="login_button">Giriş Yap</string>
<string name="login_register_link">Hesabın yok mu? Kayıt Ol</string>

<!-- Register Screen -->
<string name="register_title">Hesap Oluştur</string>
<string name="register_name_hint">Adınız Soyadınız</string>
<string name="register_email_hint">E-posta Adresiniz</string>
<string name="register_password_hint">Şifre Belirleyin</string>
<string name="register_password_confirm_hint">Şifrenizi Onaylayın</string>
<string name="register_button">Kayıt Ol</string>
<string name="register_login_link">Zaten bir hesabın var mı? Giriş Yap</string>

<!-- Home Screen -->
<string name="home_title">Günün Menüsü</string>
<string name="category_soup">Çorba</string>
<string name="category_main_dish">Ana Yemek</string>
<string name="category_side_dish">Yardımcı Yemek</string>
<string name="category_dessert">Tatlı / Meyve</string>

<!-- Rating Screen -->
<string name="rating_title">Yemeği Değerlendir</string>
<string name="rating_label">Puanınız:</string>
<string name="rating_comment_label">Yorumunuz</string>
<string name="rating_comment_hint">Bu yemek hakkındaki düşüncelerinizi yazın...</string>
<string name="rating_submit_button">Gönder</string>

<!-- History Screen -->
<string name="history_title">Geçmiş Menüler</string>
<string name="history_description">Görüntülemek istediğiniz tarihi seçin.</string>

<!-- Bottom Navigation -->
<string name="nav_today">Bugün</string>
<string name="nav_history">Geçmiş</string>
<string name="nav_profile">Profil</string>
```

---

## Navigasyon Yapısı

### Navigation Graph
```
AuthNavGraph
├── LoginScreen
└── RegisterScreen

MainNavGraph
├── BottomNavigation
│   ├── HomeScreen (Tab: Bugün)
│   ├── HistoryScreen (Tab: Geçmiş)
│   └── ProfileScreen (Tab: Profil)
└── RatingScreen (Detached)
```

---

## Veri Modelleri

### User
```kotlin
data class User(
    val id: String,
    val name: String,
    val email: String
)
```

### MenuItem
```kotlin
data class MenuItem(
    val id: String,
    val name: String,
    val category: MenuCategory,
    val date: LocalDate,
    val imageUrl: String? = null
)

enum class MenuCategory {
    SOUP,        // Çorba
    MAIN_DISH,   // Ana Yemek
    SIDE_DISH,   // Yardımcı Yemek
    DESSERT      // Tatlı / Meyve
}
```

### Rating
```kotlin
data class Rating(
    val id: String,
    val userId: String,
    val menuItemId: String,
    val stars: Int, // 1-5
    val comment: String? = null,
    val timestamp: LocalDateTime
)
```

### DailyMenu
```kotlin
data class DailyMenu(
    val date: LocalDate,
    val soup: MenuItem?,
    val mainDish: MenuItem?,
    val sideDish: MenuItem?,
    val dessert: MenuItem?
)
```

---

## UI/UX Kuralları

### Renk Paleti
- Primary Color: Kampüs teması (mavi/yeşil tonları önerilir)
- Background: Light/Dark mode desteği
- Success Color: Yeşil (başarılı işlemler için)
- Error Color: Kırmızı (hata mesajları için)

### Tipografi
- Başlıklar: Material Design Typography H5-H6
- Body Text: Material Design Typography Body1-Body2
- Button Text: Material Design Typography Button

### Spacing
- Card padding: 16dp
- Section spacing: 24dp
- Element spacing: 8dp

### Animasyonlar
- Screen transitions: Fade in/out
- Card press: Ripple effect
- Rating stars: Scale animation

---

## Geliştirme Adımları

### Faz 1: Proje Kurulumu
1. Gradle dependencies ekleme
2. String resources tanımlama
3. Theme ve Color ayarları

### Faz 2: Temel UI Bileşenleri
1. Custom composables (TextField, Button, Card, etc.)
2. Bottom Navigation Bar
3. Navigation setup

### Faz 3: Kimlik Doğrulama
1. LoginScreen implementasyonu
2. RegisterScreen implementasyonu
3. Validation logic
4. Navigation flow

### Faz 4: Ana Özellikler
1. HomeScreen (Günün Menüsü)
2. RatingScreen (Puanlama)
3. HistoryScreen (Geçmiş)
4. ProfileScreen

### Faz 5: Veri Entegrasyonu
1. Local data storage (Room/DataStore)
2. Backend integration (opsiyonel)
3. State management (ViewModel)

### Faz 6: Polish ve Test
1. UI testleri
2. Animasyonlar
3. Error handling
4. Performance optimization

---

## Test Gereksinimleri

### UI Tests
- Login/Register flow testleri
- Navigation testleri
- Rating submission testleri

### Unit Tests
- Validation logic testleri
- ViewModel testleri
- Data model testleri

---

## Notlar ve En İyi Pratikler

1. **Compose Best Practices:**
   - State hoisting kullanın
   - remember ve rememberSaveable kullanımına dikkat edin
   - Side effects için LaunchedEffect kullanın

2. **Accessibility:**
   - Tüm bileşenlere contentDescription ekleyin
   - Minimum touch target size: 48dp
   - Contrast ratio standartlarına uyun

3. **Performance:**
   - Lazy loading kullanın
   - Image loading için Coil veya Glide kullanın
   - Unnecessary recomposition'dan kaçının

4. **Security:**
   - Şifreleri asla plain text olarak saklamayın
   - SSL pinning kullanın (backend varsa)
   - Input validation her zaman yapın

---

## Ek Kaynaklar

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Android Architecture Components](https://developer.android.com/topic/architecture)
