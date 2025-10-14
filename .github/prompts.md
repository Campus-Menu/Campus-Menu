# 🎓 Kampüs Menüsü Uygulaması – AI Tasarım Prompt & Instruction

## 🧭 PROJECT OVERVIEW
**Project Name:** Kampüs Menüsü  
**Goal:**  
Create a clean, modern, and offline-friendly mobile application interface that displays the university dining hall menu, allows students to rate meals (1–5 stars), and browse past menus.  
App must be **natively designed** for **iOS (SwiftUI)** and **Android (Kotlin Jetpack Compose)**.

---
## 🧠 PROMPT

Design a *cross-platform native mobile app* called *“Kampüs Menüsü”* for university students to:
- View today’s dining hall menu  
- Rate the meals (1–5 stars)  
- Browse archived menus  
- Work offline with local JSON data  
- Enjoy a simple, friendly interface with clear typography and icons  

Use *SwiftUI design language* for iOS and *Material 3* for Android.  
The design should reflect freshness, simplicity, and clarity — evoking the feel of campus life and healthy meals.

---

## 🎨 STYLE GUIDE

### 🧩 General Aesthetic
- Minimalist, clean layout  
- Rounded cards and soft shadows  
- Use white space generously  
- Smooth micro-animations for star rating and tab transitions

### 🎨 Colors
| Element | Color | Notes |
|----------|--------|-------|
| Primary | #4CAF50 | Green – freshness, nature |
| Secondary | #FFC107 | Yellow – energy |
| Background | #FAFAFA | Neutral light background |
| Text Primary | #212121 | High contrast |
| Text Secondary | #757575 | For details or kcal info |

### 🖋 Typography
| Platform | Font | Weight |
|-----------|-------|--------|
| iOS | SF Pro Rounded | Regular / Bold |
| Android | Roboto | Regular / Medium |

### 🧠 Icon Set
- 🍽 “Today” → calendar  
- 🕓 “Archive” → history  
- ⭐ “Rating” → star.fill / star  
- ⚙ “Settings” → gearshape

---

## 📱 SCREEN LIST

### ⿡ Splash Screen
- App logo centered (“Kampüs Menüsü”)  
- Optional loading animation  

### ⿢ Main Navigation (Bottom Tab Bar)
Tabs:  
- *Today (Bugün)*  
- *Archive (Arşiv)*  
- *Settings (Ayarlar)*

### ⿣ Today Screen
- Header: “Bugünün Menüsü”  
- Card with today’s meals (list of dishes, kcal)  
- 5-star rating component  
- Subtext under stars (“Henüz puanlanmadı”, “İyi”, etc.)

### ⿤ Archive Screen
- Search bar at top  
- Scrollable list of past menus  
- Each item → date + short meal list preview  
- On tap → open menu detail card

### ⿥ Settings Screen
- Buttons:
  - “JSON’u Yeniden Yükle”  
  - “Tüm Puanları Sıfırla”  
- About section (App name + version)

---

## 🧭 INSTRUCTIONS FOR AI DESIGN TOOL

*1. Layout Rules*
- Use *8-point grid system*
- Each screen should be *responsive* (iPhone 14 / Pixel 8 base)
- Maintain consistent spacing between cards and elements

*2. Component Naming*
- Prefix shared components with KM_ (e.g., KM_StarRating, KM_MenuCard)
- Organize screens under /screens folder:
  - /screens/today
  - /screens/archive
  - /screens/settings

*3. Interaction States*
- Star icons change color on tap  
- Bottom navigation highlights selected tab  
- Search bar expands when focused  
- Buttons use ripple/tap feedback  

*4. Export Assets*
- Export icons as SVG (light/dark variants)  
- Export color variables as design tokens (JSON)

*5. Accessibility*
- Contrast ratio ≥ 4.5:1  
- Texts scalable (Dynamic Type for iOS / ScalableText for Android)  
- VoiceOver: announce menu name and kcal values

---

## 💡 FUTURE EXPANSIONS
- Add Firebase sync for average meal ratings  
- Push notification: “Bugünkü menü hazır 🍽”  
- Meal detail screen with comments  
- Dark mode design variant

---

### ✅ FINAL NOTE
Focus on creating a *realistic, developer-ready UI* that aligns visually across both platforms but respects *native interaction standards*.  
The final design should be ready for export to SwiftUI components and Jetpack Compose UI elements.

