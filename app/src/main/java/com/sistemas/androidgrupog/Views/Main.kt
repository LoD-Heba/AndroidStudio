package com.sistemas.androidgrupog.Views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun Main() {
    var showLogin by remember { mutableStateOf(true) }

    if (showLogin) {
        LoginScreen(
            onNavigateToRegister = { showLogin = false }
        )
    } else {
        RegisterScreen(
            onBackToLogin = { showLogin = true },
            onRegister = { name, email, password ->
                // Lógica para guardar el usuario
            }
        )
    }
}

@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit) {

}
