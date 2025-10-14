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
