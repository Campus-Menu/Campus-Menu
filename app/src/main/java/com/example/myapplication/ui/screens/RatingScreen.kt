package com.example.myapplication.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingScreen(
    menuItemId: String = "1",
    menuItemName: String = "Mercimek Çorbası",
    menuItemCategory: MenuCategory = MenuCategory.SOUP,
    onBackClick: () -> Unit = {},
    onSubmitRating: (Int, String) -> Unit = { _, _ -> }
) {
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    
    val categoryColor = when (menuItemCategory) {
        MenuCategory.SOUP -> SoupColor
        MenuCategory.MAIN_DISH -> MainDishColor
        MenuCategory.SIDE_DISH -> SideDishColor
        MenuCategory.DESSERT -> DessertColor
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Değerlendirme",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Geri"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Yemek Fotoğrafı ve Başlık
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                categoryColor.copy(alpha = 0.4f),
                                categoryColor.copy(alpha = 0.2f),
                                Color.Transparent
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Yemek İkonu/Fotoğrafı
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(24.dp))
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
                        // Gerçekçi yemek emoji görseli
                        Text(
                            text = when (menuItemCategory) {
                                MenuCategory.SOUP -> "🍲"
                                MenuCategory.MAIN_DISH -> when {
                                    menuItemName.contains("Tavuk", ignoreCase = true) -> "🍗"
                                    menuItemName.contains("Köfte", ignoreCase = true) -> "🍖"
                                    menuItemName.contains("Balık", ignoreCase = true) -> "🐟"
                                    else -> "🍽️"
                                }
                                MenuCategory.SIDE_DISH -> when {
                                    menuItemName.contains("Pilav", ignoreCase = true) -> "🍚"
                                    menuItemName.contains("Makarna", ignoreCase = true) -> "🍝"
                                    menuItemName.contains("Salata", ignoreCase = true) -> "🥗"
                                    else -> "🥘"
                                }
                                MenuCategory.DESSERT -> when {
                                    menuItemName.contains("Sütlaç", ignoreCase = true) -> "🍮"
                                    menuItemName.contains("Kek", ignoreCase = true) -> "🍰"
                                    menuItemName.contains("Meyve", ignoreCase = true) -> "🍎"
                                    menuItemName.contains("Muhallebi", ignoreCase = true) -> "🥛"
                                    menuItemName.contains("Komposto", ignoreCase = true) -> "🍑"
                                    else -> "🧁"
                                }
                            },
                            fontSize = 64.sp
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Yemek Adı
                    Text(
                        text = menuItemName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Text(
                        text = "Menü değerlendirmemize yardımcı olun",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Puanlama Başlığı
                Text(
                    text = "Puanınız",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
                )

                // Animasyonlu Yıldızlar
                AnimatedStarRating(
                    rating = rating,
                    onRatingChanged = { rating = it },
                    categoryColor = categoryColor
                )

                // Seçilen Puan Göstergesi
                AnimatedVisibility(visible = rating > 0) {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = categoryColor.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = when (rating) {
                                1 -> "😞 Çok Kötü"
                                2 -> "😕 Kötü"
                                3 -> "😐 İdare Eder"
                                4 -> "🙂 İyi"
                                5 -> "😍 Mükemmel"
                                else -> ""
                            },
                            style = MaterialTheme.typography.titleLarge,
                            color = categoryColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Yorum Başlığı
                Text(
                    text = "Yorumunuz",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                // Yorum TextField
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = { Text("Ne düşündünüz?") },
                    maxLines = 5,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = categoryColor,
                        focusedLabelColor = categoryColor
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                // Gönder Butonu
                Button(
                    onClick = {
                        if (rating > 0) {
                            onSubmitRating(rating, comment)
                            showSuccessDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(56.dp),
                    enabled = rating > 0,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = categoryColor,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Değerlendirmeyi Gönder",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }

        // Başarı Dialog
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { },
                title = { 
                    Text(
                        "✅ Teşekkürler!",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                text = { 
                    Text("Değerlendirmeniz başarıyla kaydedildi. Görüşleriniz bizim için çok değerli!") 
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccessDialog = false
                            onBackClick()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = categoryColor
                        )
                    ) {
                        Text("Tamam")
                    }
                },
                shape = RoundedCornerShape(24.dp)
            )
        }
    }
}

@Composable
fun AnimatedStarRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    categoryColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        for (i in 1..5) {
            val isSelected = i <= rating
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.2f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "star_scale_$i"
            )
            
            Icon(
                imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.StarOutline,
                contentDescription = "$i yıldız",
                modifier = Modifier
                    .size(64.dp)
                    .scale(scale)
                    .clickable { onRatingChanged(i) }
                    .padding(4.dp),
                tint = if (isSelected) categoryColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RatingScreenPreview() {
    MyApplicationTheme {
        RatingScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RatingScreenDarkPreview() {
    MyApplicationTheme {
        RatingScreen()
    }
}
