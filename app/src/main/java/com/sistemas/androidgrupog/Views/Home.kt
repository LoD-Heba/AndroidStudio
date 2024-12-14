package com.sistemas.androidgrupog.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sistemas.androidgrupog.R


@Composable
fun HomeScreen(userName: String, onCategoryClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "¡Hola, $userName! 🌱",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Categorías
        Text(text = "Categorías", style = MaterialTheme.typography.titleMedium)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            val categories = listOf("Arbustos", "Flores", "Exterior", "Interior")
            items(categories) { category ->
                Card(
                    modifier = Modifier.size(150.dp),
                    onClick = { onCategoryClick(category) }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = category, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        // Destacados
        Text(text = "Destacados", style = MaterialTheme.typography.titleMedium)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(5) { index ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /* Lógica de ver más */ }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logoclub),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Planta $index", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "$${index * 10} Bs", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    // Llama al Composable principal y pasa valores de prueba
    HomeScreen(
        userName = "Usuario de Prueba",
        onCategoryClick = { category ->
            // Solo para pruebas; puedes imprimir el clic en consola
            println("Categoría seleccionada: $category")
        }
    )
}
