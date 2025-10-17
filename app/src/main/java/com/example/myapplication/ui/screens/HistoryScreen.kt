package com.example.myapplication.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// Günlük Menü Data Class
data class DailyMenu(
    val date: LocalDate,
    val items: List<MenuItem>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateToRating: (String) -> Unit = {}
) {
    var expandedDateId by remember { mutableStateOf<String?>(null) }
    
    // Örnek geçmiş menüler (normalde veritabanından gelecek)
    val pastMenus = remember {
        listOf(
            DailyMenu(
                date = LocalDate.now().minusDays(1),
                items = listOf(
                    MenuItem("11", "Domates Çorbası", MenuCategory.SOUP, "Rich tomato soup", 12.00, "soup"),
                    MenuItem("12", "Izgara Köfte", MenuCategory.MAIN_DISH, "Grilled meatballs", 40.00, "main"),
                    MenuItem("13", "Makarna", MenuCategory.SIDE_DISH, "Pasta with sauce", 18.00, "side"),
                    MenuItem("14", "Kek", MenuCategory.DESSERT, "Chocolate cake", 20.00, "dessert")
                )
            ),
            DailyMenu(
                date = LocalDate.now().minusDays(2),
                items = listOf(
                    MenuItem("21", "Ezogelin Çorbası", MenuCategory.SOUP, "Traditional soup", 15.00, "soup"),
                    MenuItem("22", "Tavuk Sote", MenuCategory.MAIN_DISH, "Chicken saute", 42.00, "main"),
                    MenuItem("23", "Bulgur Pilavı", MenuCategory.SIDE_DISH, "Bulgur rice", 16.00, "side"),
                    MenuItem("24", "Komposto", MenuCategory.DESSERT, "Fruit compote", 18.00, "dessert")
                )
            ),
            DailyMenu(
                date = LocalDate.now().minusDays(3),
                items = listOf(
                    MenuItem("31", "Tarhana Çorbası", MenuCategory.SOUP, "Tarhana soup", 14.00, "soup"),
                    MenuItem("32", "Karnıyarık", MenuCategory.MAIN_DISH, "Stuffed eggplant", 38.00, "main"),
                    MenuItem("33", "Cacık", MenuCategory.SIDE_DISH, "Yogurt with cucumber", 12.00, "side"),
                    MenuItem("34", "Muhallebi", MenuCategory.DESSERT, "Milk pudding", 22.00, "dessert")
                )
            ),
            DailyMenu(
                date = LocalDate.now().minusDays(4),
                items = listOf(
                    MenuItem("41", "Mercimek Çorbası", MenuCategory.SOUP, "Lentil soup", 15.00, "soup"),
                    MenuItem("42", "Tavuk Şinitzel", MenuCategory.MAIN_DISH, "Chicken schnitzel", 45.00, "main"),
                    MenuItem("43", "Pilav", MenuCategory.SIDE_DISH, "Rice pilaf", 20.00, "side"),
                    MenuItem("44", "Sütlaç", MenuCategory.DESSERT, "Rice pudding", 25.00, "dessert")
                )
            ),
            DailyMenu(
                date = LocalDate.now().minusDays(5),
                items = listOf(
                    MenuItem("51", "Domates Çorbası", MenuCategory.SOUP, "Tomato soup", 12.00, "soup"),
                    MenuItem("52", "Izgara Köfte", MenuCategory.MAIN_DISH, "Grilled meatballs", 40.00, "main"),
                    MenuItem("53", "Bulgur Pilavı", MenuCategory.SIDE_DISH, "Bulgur pilaf", 16.00, "side"),
                    MenuItem("54", "Kek", MenuCategory.DESSERT, "Cake", 20.00, "dessert")
                )
            )
        )
    }

    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("tr", "TR"))
    val dayFormatter = DateTimeFormatter.ofPattern("EEEE", Locale("tr", "TR"))
    
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
                        Text(
                            text = "Geçmiş Menüler",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
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
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Açıklama
            item {
                Text(
                    text = "Geçmiş tarihlerdeki menüleri görüntüleyin",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Tarih Kartları
            items(pastMenus) { dailyMenu ->
                DateMenuCard(
                    dailyMenu = dailyMenu,
                    isExpanded = expandedDateId == dailyMenu.date.toString(),
                    onDateClick = {
                        expandedDateId = if (expandedDateId == dailyMenu.date.toString()) {
                            null
                        } else {
                            dailyMenu.date.toString()
                        }
                    },
                    onMenuItemClick = onNavigateToDetail,
                    dateFormatter = dateFormatter,
                    dayFormatter = dayFormatter
                )
            }
        }
    }
}

@Composable
fun DateMenuCard(
    dailyMenu: DailyMenu,
    isExpanded: Boolean,
    onDateClick: () -> Unit,
    onMenuItemClick: (String) -> Unit,
    dateFormatter: DateTimeFormatter,
    dayFormatter: DateTimeFormatter
) {
    val formattedDate = dailyMenu.date.format(dateFormatter)
    val dayName = dailyMenu.date.format(dayFormatter)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onDateClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isExpanded) 4.dp else 2.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Tarih Başlığı
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Takvim İkonu
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isExpanded)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.primaryContainer
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = null,
                            tint = if (isExpanded) Color.White else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = dayName.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (isExpanded)
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isExpanded)
                                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (isExpanded) "Daralt" else "Genişlet",
                    tint = if (isExpanded)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Menü İtemları (Genişletildiğinde)
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Divider(
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    
                    dailyMenu.items.forEach { menuItem ->
                        MenuItemCard(
                            menuItem = menuItem,
                            onClick = { onMenuItemClick(menuItem.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryScreenPreview() {
    MyApplicationTheme {
        HistoryScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HistoryScreenDarkPreview() {
    MyApplicationTheme {
        HistoryScreen()
    }
}
