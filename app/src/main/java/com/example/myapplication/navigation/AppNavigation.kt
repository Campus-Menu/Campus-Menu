package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.HistoryScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.MainScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.RatingScreen
import com.example.myapplication.ui.screens.RegisterScreen
import com.example.myapplication.ui.screens.MenuItemDetailScreen
import com.example.myapplication.ui.screens.MenuItemDetail
import com.example.myapplication.ui.screens.MenuCategory
import com.example.myapplication.ui.screens.NutritionInfo
import com.example.myapplication.viewmodels.AuthViewModel

// Ekran route'ları
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object MenuDetail : Screen("menu_detail/{menuItemId}") {
        fun createRoute(menuItemId: String) = "menu_detail/$menuItemId"
    }
    data object Rating : Screen("rating/{menuItemId}") {
        fun createRoute(menuItemId: String) = "rating/$menuItemId"
    }
    data object History : Screen("history")
    data object Profile : Screen("profile")
}

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,
    navController: NavHostController = rememberNavController(),
    startDestination: String = if (authViewModel.isAuthenticated) Screen.Home.route else Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login Ekranı
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                authViewModel = authViewModel
            )
        }

        // Register Ekranı
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                authViewModel = authViewModel
            )
        }

        // Home Ekranı (Bottom Navigation ile)
        composable(Screen.Home.route) {
            MainScreen(
                onNavigateToDetail = { menuItemId ->
                    navController.navigate(Screen.MenuDetail.createRoute(menuItemId))
                },
                onNavigateToRating = { menuItemId ->
                    navController.navigate(Screen.Rating.createRoute(menuItemId))
                },
                onLogout = {
                    // Çıkış yap - Login ekranına dön
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // Menü Detay Ekranı
        composable(
            route = "menu_detail/{menuItemId}",
            arguments = listOf(navArgument("menuItemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val menuItemId = backStackEntry.arguments?.getString("menuItemId") ?: ""
            // Örnek veri - normalde ID'ye göre veri çekilir
            val menuItem = getMenuItemDetailById(menuItemId)
            
            MenuItemDetailScreen(
                menuItem = menuItem,
                onBackClick = {
                    navController.popBackStack()
                },
                onRateClick = {
                    navController.navigate(Screen.Rating.createRoute(menuItemId))
                }
            )
        }

        // Rating Ekranı
        composable(
            route = "rating/{menuItemId}",
            arguments = listOf(navArgument("menuItemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val menuItemId = backStackEntry.arguments?.getString("menuItemId") ?: ""
            // Basit bir örnek - normalde ID'ye göre veri çekilir
            val (menuItemName, menuItemCategory) = when (menuItemId) {
                "1" -> "Mercimek Çorbası" to MenuCategory.SOUP
                "2" -> "Tavuk Şinitzel" to MenuCategory.MAIN_DISH
                "3" -> "Pilav" to MenuCategory.SIDE_DISH
                "4" -> "Sütlaç" to MenuCategory.DESSERT
                "11" -> "Domates Çorbası" to MenuCategory.SOUP
                "12" -> "Izgara Köfte" to MenuCategory.MAIN_DISH
                "13" -> "Makarna" to MenuCategory.SIDE_DISH
                "14" -> "Kek" to MenuCategory.DESSERT
                "21" -> "Ezogelin Çorbası" to MenuCategory.SOUP
                "22" -> "Tavuk Sote" to MenuCategory.MAIN_DISH
                "23" -> "Bulgur Pilavı" to MenuCategory.SIDE_DISH
                "24" -> "Komposto" to MenuCategory.DESSERT
                "31" -> "Tarhana Çorbası" to MenuCategory.SOUP
                "32" -> "Karnıyarık" to MenuCategory.MAIN_DISH
                "33" -> "Cacık" to MenuCategory.SIDE_DISH
                "34" -> "Muhallebi" to MenuCategory.DESSERT
                else -> "Yemek" to MenuCategory.MAIN_DISH
            }
            
            RatingScreen(
                menuItemId = menuItemId,
                menuItemName = menuItemName,
                menuItemCategory = menuItemCategory,
                onBackClick = {
                    navController.popBackStack()
                },
                onSubmitRating = { rating, comment ->
                    // TODO: Rating'i kaydet
                    // Şimdilik sadece geri dön
                }
            )
        }

        // History ve Profile ekranları artık MainScreen içinde Bottom Nav ile erişiliyor
        // Rating ekranı ayrı bir route olarak kalıyor

        // TODO: Gerekirse ek ekranlar eklenebilir
    }
}

// Örnek veri fonksiyonu - Normalde repository'den gelecek
fun getMenuItemDetailById(id: String): MenuItemDetail {
    return when (id) {
        "1" -> MenuItemDetail(
            id = "1",
            name = "Mercimek Çorbası",
            category = MenuCategory.SOUP,
            description = "Vitamin ve mineral açısından zengin, sağlıklı ve lezzetli bir çorba. Kırmızı mercimek, taze sebzeler ve özenle seçilmiş baharatlarla hazırlanır. Soğuk kış günlerinde ısınmanız için ideal bir seçim.",
            ingredients = listOf(
                "Kırmızı mercimek (200g)",
                "Soğan (1 adet, orta boy)",
                "Havuç (1 adet)",
                "Patates (1 adet)",
                "Domates salçası (1 yemek kaşığı)",
                "Un (1 tatlı kaşığı)",
                "Tereyağı (1 yemek kaşığı)",
                "Tuz, karabiber, kırmızı biber"
            ),
            allergens = listOf("Süt ürünleri (Tereyağı)", "Gluten (Un)"),
            nutrition = NutritionInfo(
                calories = 180,
                protein = 9.5,
                carbs = 28.0,
                fat = 3.2,
                fiber = 6.5
            ),
            portionSize = "1 porsiyon (250ml)",
            averageRating = 4.5,
            totalRatings = 127
        )
        "2" -> MenuItemDetail(
            id = "2",
            name = "Tavuk Şinitzel",
            category = MenuCategory.MAIN_DISH,
            description = "Özel baharatlarla marine edilmiş tavuk göğsü, çıtır çıtır galeta unuyla kaplanarak altın rengi olana kadar kızartılır. Yanında taze yeşillik ve limon servisi ile sunulur.",
            ingredients = listOf(
                "Tavuk göğsü filetosu (150g)",
                "Galeta unu",
                "Yumurta",
                "Un",
                "Tuz, karabiber",
                "Kekik, kimyon",
                "Ayçiçek yağı (kızartma için)"
            ),
            allergens = listOf("Yumurta", "Gluten"),
            nutrition = NutritionInfo(
                calories = 320,
                protein = 28.0,
                carbs = 18.0,
                fat = 15.0,
                fiber = 1.2
            ),
            portionSize = "1 porsiyon (150g)",
            averageRating = 4.2,
            totalRatings = 98
        )
        "3" -> MenuItemDetail(
            id = "3",
            name = "Pilav",
            category = MenuCategory.SIDE_DISH,
            description = "Tereyağında kavrulmuş şehriye ile birlikte pişirilen, tane tane olgunlaşmış beyaz pirinç pilavı. Her ana yemeğin yanında mükemmel bir tamamlayıcı.",
            ingredients = listOf(
                "Baldo pirinç (100g)",
                "Şehriye",
                "Tereyağı (1 yemek kaşığı)",
                "Tavuk suyu",
                "Tuz"
            ),
            allergens = listOf("Süt ürünleri", "Gluten (Şehriye)"),
            nutrition = NutritionInfo(
                calories = 250,
                protein = 4.5,
                carbs = 52.0,
                fat = 4.0,
                fiber = 0.8
            ),
            portionSize = "1 porsiyon (200g)",
            averageRating = 4.7,
            totalRatings = 156
        )
        "4" -> MenuItemDetail(
            id = "4",
            name = "Sütlaç",
            category = MenuCategory.DESSERT,
            description = "Geleneksel Türk tatlısı sütlaç, yavaş ateşte pişirilmiş pirincin süt ve şeker ile buluşmasından doğar. Üzeri fırında hafifçe kızartılarak sunulur.",
            ingredients = listOf(
                "Süt (1 litre)",
                "Pirinç (100g)",
                "Şeker (100g)",
                "Pirinç nişastası",
                "Vanilya",
                "Tarçın (süsleme için)"
            ),
            allergens = listOf("Süt ve süt ürünleri"),
            nutrition = NutritionInfo(
                calories = 220,
                protein = 6.0,
                carbs = 38.0,
                fat = 4.5,
                fiber = 0.3
            ),
            portionSize = "1 porsiyon (150g)",
            averageRating = 4.8,
            totalRatings = 203
        )
        // Geçmiş Menüler - Gün 1
        "11" -> MenuItemDetail(
            id = "11",
            name = "Domates Çorbası",
            category = MenuCategory.SOUP,
            description = "Taze domateslerin kremalı dokusuyla birleştiği zengin ve lezzetli çorba. Fesleğen yaprakları ile servis edilir.",
            ingredients = listOf(
                "Domates (500g)",
                "Soğan (1 adet)",
                "Sarımsak (2 diş)",
                "Tereyağı",
                "Un",
                "Krema",
                "Fesleğen",
                "Tuz, karabiber"
            ),
            allergens = listOf("Süt ürünleri (Krema, Tereyağı)", "Gluten"),
            nutrition = NutritionInfo(
                calories = 160,
                protein = 4.0,
                carbs = 20.0,
                fat = 8.0,
                fiber = 3.5
            ),
            portionSize = "1 porsiyon (250ml)",
            averageRating = 4.3,
            totalRatings = 89
        )
        "12" -> MenuItemDetail(
            id = "12",
            name = "Izgara Köfte",
            category = MenuCategory.MAIN_DISH,
            description = "Dana kıymasından hazırlanan, odun ateşinde ızgarada pişirilen enfes köfteler. Soğan, maydanoz ve özel baharatlarla lezzetlendirilir.",
            ingredients = listOf(
                "Dana kıyma (200g)",
                "Soğan",
                "Maydanoz",
                "Ekmek içi",
                "Yumurta",
                "Kimyon, pul biber",
                "Tuz, karabiber"
            ),
            allergens = listOf("Yumurta", "Gluten"),
            nutrition = NutritionInfo(
                calories = 380,
                protein = 32.0,
                carbs = 12.0,
                fat = 22.0,
                fiber = 1.0
            ),
            portionSize = "1 porsiyon (4 adet)",
            averageRating = 4.6,
            totalRatings = 145
        )
        "13" -> MenuItemDetail(
            id = "13",
            name = "Makarna",
            category = MenuCategory.SIDE_DISH,
            description = "Al dente pişirilmiş makarna, domates soslu veya kremalı sos seçenekleri ile. Üzeri rendelenmiş kaşar peyniri ile servis edilir.",
            ingredients = listOf(
                "Makarna (burgu veya penne)",
                "Domates sosu",
                "Sarımsak",
                "Zeytinyağı",
                "Kaşar peyniri",
                "Fesleğen",
                "Tuz, karabiber"
            ),
            allergens = listOf("Gluten", "Süt ürünleri (Kaşar)"),
            nutrition = NutritionInfo(
                calories = 280,
                protein = 10.0,
                carbs = 48.0,
                fat = 6.0,
                fiber = 3.0
            ),
            portionSize = "1 porsiyon (200g)",
            averageRating = 4.4,
            totalRatings = 112
        )
        "14" -> MenuItemDetail(
            id = "14",
            name = "Kek",
            category = MenuCategory.DESSERT,
            description = "Kabarık ve nemli yapısıyla vanilyalı kek. Çikolata sosu veya pudra şekeri ile süslenir.",
            ingredients = listOf(
                "Un",
                "Şeker",
                "Yumurta",
                "Süt",
                "Tereyağı",
                "Vanilya",
                "Kabartma tozu",
                "Kakao (çikolatalı versiyon için)"
            ),
            allergens = listOf("Gluten", "Yumurta", "Süt ürünleri"),
            nutrition = NutritionInfo(
                calories = 240,
                protein = 5.0,
                carbs = 35.0,
                fat = 9.0,
                fiber = 1.0
            ),
            portionSize = "1 dilim (100g)",
            averageRating = 4.5,
            totalRatings = 87
        )
        // Geçmiş Menüler - Gün 2
        "21" -> MenuItemDetail(
            id = "21",
            name = "Ezogelin Çorbası",
            category = MenuCategory.SOUP,
            description = "Kırmızı mercimek, bulgur ve pirinç ile yapılan geleneksel Türk çorbası. Nane ve kırmızı biberli tereyağı ile servis edilir.",
            ingredients = listOf(
                "Kırmızı mercimek",
                "Bulgur",
                "Pirinç",
                "Soğan",
                "Domates salçası",
                "Biber salçası",
                "Tereyağı",
                "Nane, pul biber"
            ),
            allergens = listOf("Süt ürünleri (Tereyağı)", "Gluten"),
            nutrition = NutritionInfo(
                calories = 195,
                protein = 10.0,
                carbs = 32.0,
                fat = 3.5,
                fiber = 7.0
            ),
            portionSize = "1 porsiyon (250ml)",
            averageRating = 4.7,
            totalRatings = 156
        )
        "22" -> MenuItemDetail(
            id = "22",
            name = "Tavuk Sote",
            category = MenuCategory.MAIN_DISH,
            description = "Renkli sebzelerle birlikte wok'ta pişirilmiş tavuk parçaları. Soya sosu ve özel baharatlarla tatlandırılır.",
            ingredients = listOf(
                "Tavuk göğsü",
                "Biber (kırmızı, yeşil, sarı)",
                "Soğan",
                "Mantar",
                "Soya sosu",
                "Sarımsak",
                "Zencefil",
                "Susam yağı"
            ),
            allergens = listOf("Soya", "Susam"),
            nutrition = NutritionInfo(
                calories = 290,
                protein = 30.0,
                carbs = 15.0,
                fat = 12.0,
                fiber = 3.5
            ),
            portionSize = "1 porsiyon (200g)",
            averageRating = 4.4,
            totalRatings = 98
        )
        "23" -> MenuItemDetail(
            id = "23",
            name = "Bulgur Pilavı",
            category = MenuCategory.SIDE_DISH,
            description = "Pirinç gibi pişirilmiş, tane tane bulgur pilavı. Domates ve biberle renklendirilerek tereyağında kavrulur.",
            ingredients = listOf(
                "Köftelik bulgur",
                "Domates",
                "Biber",
                "Soğan",
                "Tereyağı",
                "Domates salçası",
                "Tuz, karabiber"
            ),
            allergens = listOf("Süt ürünleri (Tereyağı)", "Gluten"),
            nutrition = NutritionInfo(
                calories = 210,
                protein = 6.0,
                carbs = 42.0,
                fat = 3.0,
                fiber = 8.5
            ),
            portionSize = "1 porsiyon (180g)",
            averageRating = 4.5,
            totalRatings = 134
        )
        "24" -> MenuItemDetail(
            id = "24",
            name = "Komposto",
            category = MenuCategory.DESSERT,
            description = "Mevsim meyvelerinden hazırlanan hafif şekerli komposto. Soğuk servis edilir, ferahlatıcı bir tatlı alternatifi.",
            ingredients = listOf(
                "Elma",
                "Armut",
                "Kayısı",
                "Şeker",
                "Tarçın",
                "Karanfil",
                "Limon suyu"
            ),
            allergens = emptyList(),
            nutrition = NutritionInfo(
                calories = 120,
                protein = 0.5,
                carbs = 30.0,
                fat = 0.2,
                fiber = 3.0
            ),
            portionSize = "1 porsiyon (150g)",
            averageRating = 4.1,
            totalRatings = 76
        )
        // Geçmiş Menüler - Gün 3
        "31" -> MenuItemDetail(
            id = "31",
            name = "Tarhana Çorbası",
            category = MenuCategory.SOUP,
            description = "Fermente edilmiş tarhana hamuru ile yapılan geleneksel çorba. Yoğurt, domates ve baharatların uyumuyla benzersiz bir tat.",
            ingredients = listOf(
                "Tarhana",
                "Domates salçası",
                "Tereyağı",
                "Kuru nane",
                "Pul biber",
                "Tuz"
            ),
            allergens = listOf("Süt ürünleri (Yoğurt içerir)", "Gluten"),
            nutrition = NutritionInfo(
                calories = 175,
                protein = 8.0,
                carbs = 28.0,
                fat = 4.0,
                fiber = 5.5
            ),
            portionSize = "1 porsiyon (250ml)",
            averageRating = 4.6,
            totalRatings = 142
        )
        "32" -> MenuItemDetail(
            id = "32",
            name = "Karnıyarık",
            category = MenuCategory.MAIN_DISH,
            description = "İçi kıymalı, soğan ve domatesten oluşan harcıyla doldurulmuş patlıcanların fırında pişirilmesi ile hazırlanan klasik Türk yemeği.",
            ingredients = listOf(
                "Patlıcan",
                "Kıyma",
                "Soğan",
                "Domates",
                "Sarımsak",
                "Biber",
                "Maydanoz",
                "Zeytinyağı",
                "Baharat"
            ),
            allergens = emptyList(),
            nutrition = NutritionInfo(
                calories = 350,
                protein = 18.0,
                carbs = 25.0,
                fat = 20.0,
                fiber = 8.0
            ),
            portionSize = "1 porsiyon (2 adet)",
            averageRating = 4.8,
            totalRatings = 187
        )
        "33" -> MenuItemDetail(
            id = "33",
            name = "Cacık",
            category = MenuCategory.SIDE_DISH,
            description = "Rendelenmiş salatalık, sarımsak ve taze naneli serinletici yoğurt mezesi. Her yemeğin yanında mükemmel bir takviye.",
            ingredients = listOf(
                "Yoğurt",
                "Salatalık",
                "Sarımsak",
                "Taze nane",
                "Dereotu",
                "Zeytinyağı",
                "Tuz"
            ),
            allergens = listOf("Süt ürünleri (Yoğurt)"),
            nutrition = NutritionInfo(
                calories = 85,
                protein = 5.0,
                carbs = 10.0,
                fat = 3.0,
                fiber = 1.5
            ),
            portionSize = "1 porsiyon (150g)",
            averageRating = 4.7,
            totalRatings = 201
        )
        "34" -> MenuItemDetail(
            id = "34",
            name = "Muhallebi",
            category = MenuCategory.DESSERT,
            description = "Süt, şeker ve pirinç unu ile pişirilen geleneksel Türk tatlısı. Üzeri tarçın veya hindistan cevizi ile süslenir.",
            ingredients = listOf(
                "Süt",
                "Şeker",
                "Pirinç unu",
                "Mısır nişastası",
                "Vanilya",
                "Tarçın (süsleme)"
            ),
            allergens = listOf("Süt ve süt ürünleri"),
            nutrition = NutritionInfo(
                calories = 190,
                protein = 5.5,
                carbs = 32.0,
                fat = 4.5,
                fiber = 0.2
            ),
            portionSize = "1 porsiyon (150g)",
            averageRating = 4.6,
            totalRatings = 165
        )
        else -> MenuItemDetail(
            id = id,
            name = "Yemek",
            category = MenuCategory.MAIN_DISH,
            description = "Günün menüsünden seçilmiş özel yemek.",
            ingredients = listOf("Çeşitli malzemeler"),
            allergens = emptyList(),
            nutrition = NutritionInfo(
                calories = 200,
                protein = 10.0,
                carbs = 25.0,
                fat = 5.0
            ),
            portionSize = "1 porsiyon"
        )
    }
}
