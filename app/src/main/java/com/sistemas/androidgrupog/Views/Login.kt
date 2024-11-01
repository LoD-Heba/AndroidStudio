package com.sistemas.androidgrupog.Views

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Login(navController:NavHostController) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.Cyan
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier.size(width = 240.dp, height = 120.dp)
    ) {

    }
}