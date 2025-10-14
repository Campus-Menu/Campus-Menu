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
