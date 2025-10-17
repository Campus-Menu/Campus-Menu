package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

@Serializable
data class User(
    val name: String,
    val email: String,
    val password: String
)

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefs = application.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val usersKey = "registered_users"
    
    var isAuthenticated by mutableStateOf(false)
        private set
    
    var currentUser by mutableStateOf<User?>(null)
        private set
    
    init {
        // Demo hesap yoksa ekle
        val users = getRegisteredUsers()
        if (users.isEmpty()) {
            val demoUser = User("Demo Kullanıcı", "demo@kampus.edu", "123456")
            saveUsers(listOf(demoUser))
        }
    }
    
    fun register(name: String, email: String, password: String): Boolean {
        val users = getRegisteredUsers().toMutableList()
        
        // Email zaten kayıtlı mı?
        if (users.any { it.email == email }) {
            return false
        }
        
        val newUser = User(name = name, email = email, password = password)
        users.add(newUser)
        
        // SharedPreferences'a kaydet (kalıcı!)
        saveUsers(users)
        
        // Otomatik giriş
        currentUser = newUser
        isAuthenticated = true
        return true
    }
    
    fun login(email: String, password: String): Boolean {
        // SharedPreferences'tan oku
        val users = getRegisteredUsers()
        val user = users.firstOrNull { 
            it.email == email && it.password == password 
        }
        
        return if (user != null) {
            currentUser = user
            isAuthenticated = true
            true
        } else {
            false
        }
    }
    
    fun logout() {
        currentUser = null
        isAuthenticated = false
    }
    
    private fun getRegisteredUsers(): List<User> {
        val json = sharedPrefs.getString(usersKey, null) ?: return emptyList()
        return try {
            Json.decodeFromString<List<User>>(json)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun saveUsers(users: List<User>) {
        val json = Json.encodeToString(users)
        sharedPrefs.edit().putString(usersKey, json).apply()
    }
}
