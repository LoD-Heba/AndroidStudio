package com.sistemas.androidgrupog.Navagation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

object MainDestination {
    const val LOGIN = "login"
    const val MAIN = "main"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
}
