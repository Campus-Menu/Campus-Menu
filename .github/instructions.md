# 📋 Campus Menu iOS - Development Instructions

## 🎯 Proje Hedefi

SwiftUI kullanarak üniversite öğrencileri için offline çalışan, temiz ve modern bir yemekhane menü uygulaması geliştirmek.

---

## 🏗️ Mimari ve Yapı

### Klasör Organizasyonu

```
Campus Menu/
├── App/
│   └── Campus_MenuApp.swift
├── Views/
│   ├── Screens/
│   │   ├── TodayView.swift
│   │   ├── ArchiveView.swift
│   │   └── SettingsView.swift
│   ├── Components/
│   │   ├── KM_StarRating.swift
│   │   ├── KM_MenuCard.swift
│   │   └── KM_SearchBar.swift
│   └── ContentView.swift (Main TabView)
├── Models/
│   ├── Menu.swift
│   ├── Meal.swift
│   └── Rating.swift
├── Services/
│   ├── MenuService.swift
│   └── RatingService.swift
├── Resources/
│   ├── menus.json
│   └── Assets.xcassets/
└── Utilities/
    ├── Constants.swift
    └── Extensions/
```

---

## 🎨 Tasarım Standartları

### Renk Paleti

swift
// Constants.swift içinde tanımlanmalı
struct AppColors {
static let primary = Color(hex: "#4CAF50") // Yeşil - tazelik
static let secondary = Color(hex: "#FFC107") // Sarı - enerji
static let background = Color(hex: "#FAFAFA") // Açık gri
static let textPrimary = Color(hex: "#212121") // Koyu gri
static let textSecondary = Color(hex: "#757575") // Orta gri
}

### Tipografi

- _Font Family:_ SF Pro Rounded
- _Title:_ .title2, Bold
- _Body:_ .body, Regular
- _Caption:_ .caption, Regular
- _Dynamic Type:_ Tüm metinler Dynamic Type desteklemeli

### Spacing (8-Point Grid System)

swift
struct Spacing {
static let xs: CGFloat = 4
static let sm: CGFloat = 8
static let md: CGFloat = 16
static let lg: CGFloat = 24
static let xl: CGFloat = 32
}

### SF Symbols İkonları

- 📅 Today: calendar
- 🕐 Archive: clock.arrow.circlepath
- ⭐ Rating: star.fill / star
- ⚙️ Settings: gearshape

---

## 📱 Ekran Gereksinimleri

### 1. ContentView (Main TabView)

- **Bottom TabBar** ile 3 ana ekran
- Aktif tab highlight edilmeli
- Tab değişimlerinde smooth animation

```swift
TabView {
    TodayView().tabItem { Label("Bugün", systemImage: "calendar") }
    ArchiveView().tabItem { Label("Arşiv", systemImage: "clock.arrow.circlepath") }
    SettingsView().tabItem { Label("Ayarlar", systemImage: "gearshape") }
}
```

### 2. TodayView

**Bileşenler:**

- Navigation Title: "Bugünün Menüsü"
- KM_MenuCard: Bugünün yemeklerini gösterir
- KM_StarRating: 5 yıldız rating sistemi
- Alt yazı: Rating durumu ("Henüz puanlanmadı", "İyi", vb.)

**Fonksiyonellik:**

- JSON'dan bugünün menüsünü oku
- Kullanıcı rating'i UserDefaults'a kaydet
- Rating değiştiğinde immediate update

### 3. ArchiveView

**Bileşenler:**

- KM_SearchBar: Tarih veya yemek adına göre arama
- Scrollable List: Geçmiş menüler (tarih azalan sırada)
- Her item: Tarih + yemek önizlemesi
- Tap → Menu detail göster

**Fonksiyonellik:**

- Real-time search filtering
- Tarih formatı: "14 Ekim 2025"
- LazyVStack kullan (performans için)

### 4. SettingsView

**Bileşenler:**

- "JSON'u Yeniden Yükle" butonu
- "Tüm Puanları Sıfırla" butonu (confirmation alert)
- About section: App adı + versiyon

**Fonksiyonellik:**

- Reload JSON → MenuService'i refresh et
- Reset ratings → UserDefaults'ı temizle
- Alert dialogları göster

---

## 💾 Veri Modelleri

### Menu Model

swift
struct Menu: Codable, Identifiable {
let id: UUID
let date: Date
let meals: [Meal]
}

### Meal Model

swift
struct Meal: Codable, Identifiable {
let id: UUID
let name: String
let calories: Int?
let type: MealType // ana yemek, yan yemek, içecek
}

enum MealType: String, Codable {
case main, side, drink
}

### Rating Model

swift
struct Rating: Codable {
let menuId: UUID
let stars: Int // 1-5
let date: Date
}

---

## 🔧 Servisler

### MenuService

_Sorumluluklar:_

- menus.json dosyasını bundle'dan oku
- JSON'u decode et
- Bugünün menüsünü getir
- Arşiv menüleri getir (filtreleme/arama desteği)

swift
class MenuService: ObservableObject {
@Published var menus: [Menu] = []

    func loadMenus()
    func getTodayMenu() -> Menu?
    func searchMenus(query: String) -> [Menu]

}

### RatingService

_Sorumluluklar:_

- UserDefaults'ta rating'leri sakla
- Rating getir/kaydet/sıfırla

swift
class RatingService {
func saveRating(for menuId: UUID, stars: Int)
func getRating(for menuId: UUID) -> Int?
func clearAllRatings()
}

---
