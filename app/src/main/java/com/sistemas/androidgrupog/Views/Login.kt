package com.sistemas.androidgrupog.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Login(navController:NavHostController) {
    var emailText by remember { mutableStateOf("")}
    Column (
        modifier = Modifier.padding(
            start = 35.dp,
            end = 35.dp,
            top = 120.dp,
            bottom = 120.dp
        ).fillMaxSize()
    ){
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.Cyan
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier.size(width = 270.dp, height = 350.dp)
        ) {
            Column {
                Row {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("Email")},
                        placeholder = { Text("Ingrese su correo")}
                    )
                }
                Row {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("Password")},
                        placeholder = { Text("Ingrese su Constraseña")}
                    )
                }
            }
        }
    }
}