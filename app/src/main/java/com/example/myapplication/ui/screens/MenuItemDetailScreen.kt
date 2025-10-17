package com.example.myapplication.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.*

// Besin Değerleri Data Class
data class NutritionInfo(
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val fiber: Double? = null
)

// Yemek Detay Data Class
data class MenuItemDetail(
    val id: String,
    val name: String,
    val category: MenuCategory,
    val description: String,
    val ingredients: List<String>,
    val allergens: List<String>,
    val nutrition: NutritionInfo,
    val portionSize: String = "1 porsiyon",
    val averageRating: Double = 0.0,
    val totalRatings: Int = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItemDetailScreen(
    menuItem: MenuItemDetail,
    onBackClick: () -> Unit = {},
    onRateClick: () -> Unit = {}
) {
    val categoryColor = when (menuItem.category) {
        MenuCategory.SOUP -> SoupColor
        MenuCategory.MAIN_DISH -> MainDishColor
        MenuCategory.SIDE_DISH -> SideDishColor
        MenuCategory.DESSERT -> DessertColor
    }

    var showNutritionDetails by remember { mutableStateOf(false) }
    var showIngredients by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yemek Detayları", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "Geri")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = categoryColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Button(
                    onClick = onRateClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = categoryColor
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Değerlendir",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Büyük Yemek Fotoğrafı
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    categoryColor.copy(alpha = 0.4f),
                                    categoryColor.copy(alpha = 0.2f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Gerçekçi yemek emoji görseli
                    Text(
                        text = when (menuItem.category) {
                            MenuCategory.SOUP -> "🍲"
                            MenuCategory.MAIN_DISH -> when {
                                menuItem.name.contains("Tavuk", ignoreCase = true) -> "🍗"
                                menuItem.name.contains("Köfte", ignoreCase = true) -> "🍖"
                                menuItem.name.contains("Balık", ignoreCase = true) -> "🐟"
                                menuItem.name.contains("Karnıyarık", ignoreCase = true) -> "🍆"
                                else -> "🍽️"
                            }
                            MenuCategory.SIDE_DISH -> when {
                                menuItem.name.contains("Pilav", ignoreCase = true) -> "🍚"
                                menuItem.name.contains("Makarna", ignoreCase = true) -> "🍝"
                                menuItem.name.contains("Salata", ignoreCase = true) -> "🥗"
                                menuItem.name.contains("Cacık", ignoreCase = true) -> "🥛"
                                else -> "🥘"
                            }
                            MenuCategory.DESSERT -> when {
                                menuItem.name.contains("Sütlaç", ignoreCase = true) -> "🍮"
                                menuItem.name.contains("Kek", ignoreCase = true) -> "🍰"
                                menuItem.name.contains("Meyve", ignoreCase = true) -> "🍎"
                                menuItem.name.contains("Komposto", ignoreCase = true) -> "🍑"
                                menuItem.name.contains("Muhallebi", ignoreCase = true) -> "🥛"
                                else -> "🧁"
                            }
                        },
                        fontSize = 120.sp
                    )
                }
            }

            // Yemek Başlığı ve İkon
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = categoryColor.copy(alpha = 0.1f)
                    ),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                categoryColor.copy(alpha = 0.7f),
                                                categoryColor
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = menuItem.category.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),
                                    tint = Color.White
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(16.dp))
                            
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = getCategoryName(menuItem.category),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = categoryColor,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = menuItem.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        
                        // Puanlama
                        if (menuItem.totalRatings > 0) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = Orange,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = String.format("%.1f", menuItem.averageRating),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = " (${menuItem.totalRatings} değerlendirme)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // Açıklama
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Description,
                                contentDescription = null,
                                tint = categoryColor,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Açıklama",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = menuItem.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Besin Değerleri (Özet)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable { showNutritionDetails = !showNutritionDetails },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = Orange,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Besin Değerleri",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Icon(
                                imageVector = if (showNutritionDetails) 
                                    Icons.Filled.ExpandLess 
                                else 
                                    Icons.Filled.ExpandMore,
                                contentDescription = null
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Kalori - Büyük Gösterim
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${menuItem.nutrition.calories}",
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = Orange
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "kalori",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                        
                        AnimatedVisibility(
                            visible = showNutritionDetails,
                            enter = expandVertically() + fadeIn()
                        ) {
                            Column(modifier = Modifier.padding(top = 16.dp)) {
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                
                                Text(
                                    text = menuItem.portionSize,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                                
                                NutritionRow("Protein", "${menuItem.nutrition.protein}g", categoryColor)
                                NutritionRow("Karbonhidrat", "${menuItem.nutrition.carbs}g", categoryColor)
                                NutritionRow("Yağ", "${menuItem.nutrition.fat}g", categoryColor)
                                menuItem.nutrition.fiber?.let {
                                    NutritionRow("Lif", "${it}g", categoryColor)
                                }
                            }
                        }
                    }
                }
            }

            // İçindekiler
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable { showIngredients = !showIngredients },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Inventory,
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "İçindekiler",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Icon(
                                imageVector = if (showIngredients) 
                                    Icons.Filled.ExpandLess 
                                else 
                                    Icons.Filled.ExpandMore,
                                contentDescription = null
                            )
                        }
                        
                        AnimatedVisibility(
                            visible = showIngredients,
                            enter = expandVertically() + fadeIn()
                        ) {
                            Column(modifier = Modifier.padding(top = 12.dp)) {
                                menuItem.ingredients.forEach { ingredient ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Circle,
                                            contentDescription = null,
                                            modifier = Modifier.size(8.dp),
                                            tint = Secondary
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = ingredient,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Alerjenler
            if (menuItem.allergens.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Error.copy(alpha = 0.1f)
                        ),
                        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = Brush.linearGradient(listOf(Error, Error)))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = null,
                                    tint = Error,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Alerjen Uyarısı",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Error
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            menuItem.allergens.forEach { allergen ->
                                Surface(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    color = Error.copy(alpha = 0.2f),
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        text = allergen,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NutritionRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MenuItemDetailScreenPreview() {
    MyApplicationTheme {
        MenuItemDetailScreen(
            menuItem = MenuItemDetail(
                id = "1",
                name = "Mercimek Çorbası",
                category = MenuCategory.SOUP,
                description = "Vitamin ve mineral açısından zengin, sağlıklı ve lezzetli bir çorba. Soğuk kış günlerinde ısınmanız için ideal.",
                ingredients = listOf(
                    "Kırmızı mercimek",
                    "Soğan",
                    "Havuç",
                    "Patates",
                    "Domates salçası",
                    "Un",
                    "Tereyağı",
                    "Tuz, karabiber"
                ),
                allergens = listOf("Süt ürünleri", "Gluten"),
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
        )
    }
}
