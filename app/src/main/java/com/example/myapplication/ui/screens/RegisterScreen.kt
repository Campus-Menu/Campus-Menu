package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.*
import com.example.myapplication.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    authViewModel: AuthViewModel
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Secondary,
                        Tertiary
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo/İkon
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 24.dp),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.2f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🎓",
                        fontSize = 56.sp
                    )
                }
            }

            // Başlık
            Text(
                text = stringResource(R.string.register_title),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Kampüs topluluğuna katıl!",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Form Kartı
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    // Ad Soyad TextField
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            showError = false
                        },
                        label = { Text(stringResource(R.string.register_name_hint)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = null,
                                tint = Secondary
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        isError = name.isNotEmpty() && name.length < 3,
                        supportingText = {
                            if (name.isNotEmpty() && name.length < 3) {
                                Text("Ad Soyad en az 3 karakter olmalıdır")
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Secondary,
                            focusedLabelColor = Secondary
                        ),
                        shape = MaterialTheme.shapes.medium
                    )

                    // E-posta TextField
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            showError = false
                        },
                        label = { Text(stringResource(R.string.register_email_hint)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = null,
                                tint = Secondary
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        isError = email.isNotEmpty() && !isValidEmail(email),
                        supportingText = {
                            if (email.isNotEmpty() && !isValidEmail(email)) {
                                Text("Geçerli bir e-posta adresi girin")
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Secondary,
                            focusedLabelColor = Secondary
                        ),
                        shape = MaterialTheme.shapes.medium
                    )

                    // Şifre TextField
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            showError = false
                        },
                        label = { Text(stringResource(R.string.register_password_hint)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = null,
                                tint = Secondary
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible)
                                        Icons.Filled.Visibility
                                    else
                                        Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible)
                                        "Şifreyi gizle"
                                    else
                                        "Şifreyi göster"
                                )
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Secondary,
                            focusedLabelColor = Secondary
                        ),
                        shape = MaterialTheme.shapes.medium
                    )

                    // Şifre Onay TextField
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            showError = false
                        },
                        label = { Text(stringResource(R.string.register_password_confirm_hint)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = null,
                                tint = Secondary
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        visualTransformation = if (confirmPasswordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible)
                                        Icons.Filled.Visibility
                                    else
                                        Icons.Filled.VisibilityOff,
                                    contentDescription = if (confirmPasswordVisible)
                                        "Şifreyi gizle"
                                    else
                                        "Şifreyi göster"
                                )
                            }
                        },
                        singleLine = true,
                        isError = confirmPassword.isNotEmpty() && confirmPassword != password,
                        supportingText = {
                            if (confirmPassword.isNotEmpty() && confirmPassword != password) {
                                Text("Şifreler eşleşmiyor")
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Secondary,
                            focusedLabelColor = Secondary
                        ),
                        shape = MaterialTheme.shapes.medium
                    )

                    // Şifre Güvenlik Göstergesi
                    if (password.isNotEmpty()) {
                        PasswordStrengthIndicator(password = password)
                        Spacer(modifier = Modifier.height(16.dp))
                    } else {
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    // Error Message
                    if (showError) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    // Kayıt Ol Butonu
                    Button(
                        onClick = {
                            if (password != confirmPassword) {
                                showError = true
                                errorMessage = "Şifreler eşleşmiyor!"
                            } else if (!isValidEmail(email)) {
                                showError = true
                                errorMessage = "Geçerli bir e-posta adresi girin!"
                            } else if (name.length < 3) {
                                showError = true
                                errorMessage = "İsim en az 3 karakter olmalı!"
                            } else if (password.length < 6) {
                                showError = true
                                errorMessage = "Şifre en az 6 karakter olmalı!"
                            } else if (authViewModel.register(name, email, password)) {
                                onNavigateToHome()
                            } else {
                                showError = true
                                errorMessage = "Bu e-posta adresi zaten kayıtlı!"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = name.length >= 3 && 
                                  isValidEmail(email) && 
                                  password.length >= 6 && 
                                  password == confirmPassword,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Secondary,
                            disabledContainerColor = Secondary.copy(alpha = 0.5f)
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = stringResource(R.string.register_button),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Giriş Yap Linki
            Surface(
                onClick = onNavigateToLogin,
                color = Color.White.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = stringResource(R.string.register_login_link),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        }
    }
}

@Composable
fun PasswordStrengthIndicator(password: String) {
    val strength = calculatePasswordStrength(password)
    val color = when (strength) {
        PasswordStrength.WEAK -> MaterialTheme.colorScheme.error
        PasswordStrength.MEDIUM -> MaterialTheme.colorScheme.tertiary
        PasswordStrength.STRONG -> MaterialTheme.colorScheme.primary
    }
    val text = when (strength) {
        PasswordStrength.WEAK -> "Zayıf"
        PasswordStrength.MEDIUM -> "Orta"
        PasswordStrength.STRONG -> "Güçlü"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Şifre Gücü:",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        LinearProgressIndicator(
            progress = { 
                when (strength) {
                    PasswordStrength.WEAK -> 0.33f
                    PasswordStrength.MEDIUM -> 0.66f
                    PasswordStrength.STRONG -> 1f
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = color,
        )
    }
}

private enum class PasswordStrength {
    WEAK, MEDIUM, STRONG
}

private fun calculatePasswordStrength(password: String): PasswordStrength {
    var score = 0
    
    // Uzunluk kontrolü
    if (password.length >= 8) score++
    if (password.length >= 12) score++
    
    // Büyük harf kontrolü
    if (password.any { it.isUpperCase() }) score++
    
    // Küçük harf kontrolü
    if (password.any { it.isLowerCase() }) score++
    
    // Rakam kontrolü
    if (password.any { it.isDigit() }) score++
    
    // Özel karakter kontrolü
    if (password.any { !it.isLetterOrDigit() }) score++
    
    return when {
        score < 3 -> PasswordStrength.WEAK
        score < 5 -> PasswordStrength.MEDIUM
        else -> PasswordStrength.STRONG
    }
}

private fun isFormValid(
    name: String,
    email: String,
    password: String,
    confirmPassword: String
): Boolean {
    return name.isNotEmpty() && name.length >= 3 &&
            isValidEmail(email) &&
            password.isNotEmpty() && password.length >= 6 &&
            password == confirmPassword
}

private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

// Preview'ler yoruma alındı - ViewModel composable içinde oluşturulamaz
/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    MyApplicationTheme {
        RegisterScreen(
            onNavigateToLogin = {},
            onNavigateToHome = {},
            authViewModel = com.example.myapplication.viewmodels.AuthViewModel(
                application = androidx.compose.ui.platform.LocalContext.current.applicationContext as android.app.Application
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RegisterScreenDarkPreview() {
    MyApplicationTheme {
        RegisterScreen(
            onNavigateToLogin = {},
            onNavigateToHome = {},
            authViewModel = com.example.myapplication.viewmodels.AuthViewModel(
                application = androidx.compose.ui.platform.LocalContext.current.applicationContext as android.app.Application
            )
        )
    }
}
*/
