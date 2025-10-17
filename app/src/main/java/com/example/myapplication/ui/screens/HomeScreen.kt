package com.example.myapplication.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RamenDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.RiceBowl
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// Menü Kategorisi Enum
enum class MenuCategory(val icon: ImageVector) {
    SOUP(Icons.Filled.RamenDining),
    MAIN_DISH(Icons.Filled.Restaurant),
    SIDE_DISH(Icons.Filled.RiceBowl),
    DESSERT(Icons.Filled.Cake)
}

// Menü Item Data Class
data class MenuItem(
    val id: String,
    val name: String,
    val category: MenuCategory,
    val description: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "", // Gerçek uygulamada URL olacak
    val date: LocalDate = LocalDate.now()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateToRating: (String) -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    // Örnek menü verileri (normalde API'den veya veritabanından gelecek)
    val todayMenu = listOf(
        MenuItem(
            id = "1",
            name = "Mercimek Çorbası",
            category = MenuCategory.SOUP,
            description = "Fluffy scrambled eggs with a side of crispy bacon and toast.",
            price = 15.00,
            imageUrl = "soup" // Placeholder
        ),
        MenuItem(
            id = "2",
            name = "Tavuk Şinitzel",
            category = MenuCategory.MAIN_DISH,
            description = "Grilled chicken breast on a bed of romaine lettuce with Caesar dressing.",
            price = 45.00,
            imageUrl = "main_dish" // Placeholder
        ),
        MenuItem(
            id = "3",
            name = "Pilav",
            category = MenuCategory.SIDE_DISH,
            description = "Pasta tossed with fresh vegetables and a light cream sauce.",
            price = 20.00,
            imageUrl = "side_dish" // Placeholder
        ),
        MenuItem(
            id = "4",
            name = "Sütlaç",
            category = MenuCategory.DESSERT,
            description = "Traditional Turkish rice pudding with cinnamon.",
            price = 25.00,
            imageUrl = "dessert" // Placeholder
        )
    )

    val currentDate = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("tr", "TR"))
    val dayFormatter = DateTimeFormatter.ofPattern("EEEE", Locale("tr", "TR"))
    val formattedDate = currentDate.format(dateFormatter)
    val dayName = currentDate.format(dayFormatter)

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
            ) {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = stringResource(R.string.home_title),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                text = formattedDate,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.9f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    },
                    actions = {
                        // Takvim Butonu
                        IconButton(
                            onClick = onNavigateToHistory,
                            modifier = Modifier
                                .padding(end = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CalendarToday,
                                contentDescription = "Geçmiş Menüler",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp)
        ) {
            // Tarih Başlığı
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = dayName.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            items(todayMenu) { menuItem ->
                MenuItemCard(
                    menuItem = menuItem,
                    onClick = { onNavigateToDetail(menuItem.id) }
                )
            }
            
            // Alt bilgi
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.tertiary
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "💡",
                                    fontSize = 28.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "İpucu",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Text(
                                text = "Yemeklere tıklayarak detayları görebilirsiniz!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuItemCard(
    menuItem: MenuItem,
    onClick: () -> Unit
) {
    // Hover animasyonu
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )
    
    val categoryColor = when (menuItem.category) {
        MenuCategory.SOUP -> SoupColor
        MenuCategory.MAIN_DISH -> MainDishColor
        MenuCategory.SIDE_DISH -> SideDishColor
        MenuCategory.DESSERT -> DessertColor
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                isPressed = true
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Yemek Fotoğrafı (Placeholder - gerçek uygulamada Coil/Glide kullanılacak)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                categoryColor.copy(alpha = 0.3f),
                                categoryColor.copy(alpha = 0.1f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Gerçekçi yemek emojileri
                Text(
                    text = when (menuItem.category) {
                        MenuCategory.SOUP -> "🍲"
                        MenuCategory.MAIN_DISH -> when {
                            menuItem.name.contains("Tavuk", ignoreCase = true) -> "🍗"
                            menuItem.name.contains("Köfte", ignoreCase = true) -> "🍖"
                            menuItem.name.contains("Balık", ignoreCase = true) -> "🐟"
                            else -> "🍽️"
                        }
                        MenuCategory.SIDE_DISH -> when {
                            menuItem.name.contains("Pilav", ignoreCase = true) -> "🍚"
                            menuItem.name.contains("Makarna", ignoreCase = true) -> "🍝"
                            menuItem.name.contains("Salata", ignoreCase = true) -> "🥗"
                            else -> "🥘"
                        }
                        MenuCategory.DESSERT -> when {
                            menuItem.name.contains("Sütlaç", ignoreCase = true) -> "🍮"
                            menuItem.name.contains("Kek", ignoreCase = true) -> "🍰"
                            menuItem.name.contains("Meyve", ignoreCase = true) -> "🍎"
                            menuItem.name.contains("Muhallebi", ignoreCase = true) -> "🥛"
                            menuItem.name.contains("Komposto", ignoreCase = true) -> "🍑"
                            else -> "🧁"
                        }
                    },
                    fontSize = 48.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // İçerik
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Kategori
                Text(
                    text = getCategoryName(menuItem.category),
                    style = MaterialTheme.typography.labelMedium,
                    color = categoryColor,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Yemek Adı
                Text(
                    text = menuItem.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Açıklama
                Text(
                    text = menuItem.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Fiyat
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "₺${String.format("%.2f", menuItem.price)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = categoryColor
                    )
                }
            }

            // Ok İşareti
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Detayları Gör",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
    
    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}

@Composable
fun getCategoryName(category: MenuCategory): String {
    return when (category) {
        MenuCategory.SOUP -> stringResource(R.string.category_soup)
        MenuCategory.MAIN_DISH -> stringResource(R.string.category_main_dish)
        MenuCategory.SIDE_DISH -> stringResource(R.string.category_side_dish)
        MenuCategory.DESSERT -> stringResource(R.string.category_dessert)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MyApplicationTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    MyApplicationTheme {
        HomeScreen()
    }
}
