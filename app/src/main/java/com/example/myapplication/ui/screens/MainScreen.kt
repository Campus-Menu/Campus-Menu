package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R

sealed class BottomNavItem(val route: String, val icon: ImageVector, val labelRes: Int) {
    object Today : BottomNavItem("home", Icons.Filled.Restaurant, R.string.nav_today)
    object History : BottomNavItem("history", Icons.Filled.History, R.string.nav_history)
    object Profile : BottomNavItem("profile", Icons.Filled.Person, R.string.nav_profile)
}

@Composable
fun MainScreen(
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateToRating: (String) -> Unit,
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    val bottomNavItems = listOf(
        BottomNavItem.Today,
        BottomNavItem.History,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ) {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(item.labelRes)
                            )
                        },
                        label = {
                            Text(stringResource(item.labelRes))
                        },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onSurface,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> HomeScreen(
                onNavigateToDetail = onNavigateToDetail,
                onNavigateToRating = onNavigateToRating,
                onNavigateToHistory = { selectedTab = 1 },
                onNavigateToProfile = { selectedTab = 2 }
            )
            1 -> HistoryScreen(
                onNavigateToDetail = onNavigateToDetail,
                onNavigateToRating = onNavigateToRating
            )
            2 -> ProfileScreen(
                userName = "Efe Uyar",
                userEmail = "efe@email.com",
                onLogout = onLogout
            )
        }
    }
}
