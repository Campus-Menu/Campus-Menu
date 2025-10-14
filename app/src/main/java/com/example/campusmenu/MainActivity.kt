package com.example.campusmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.campusmenu.ui.theme.CampusMenuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusMenuTheme {
                CampusMenuApp()
            }
        }
    }
}

@Composable
fun CampusMenuApp() {
    var showWelcomeScreen by remember { mutableStateOf(true) }
    
    if (showWelcomeScreen) {
        WelcomeScreen(
            onNavigateToHome = {
                showWelcomeScreen = false
            }
        )
    } else {
        HomeScreen()
    }
}