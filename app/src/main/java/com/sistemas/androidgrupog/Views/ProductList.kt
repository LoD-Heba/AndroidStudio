package com.sistemas.androidgrupog.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun ProductList(
    products: List<Plant>,
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (Plant) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = { println("Navegar a Home") },
                onSearchClick = { println("Navegar a lista de productos") },
                onFavoritesClick = { println("Navegar a Favoritos") },
                onProfileClick = { println("Navegar a Perfil") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF5E2))
                .padding(innerPadding) // Ajustar contenido para no superponer la barra
        ) {
            TopBar(productCount = products.size)

            // Nueva sección de filtros con íconos
            FilterSection(
                onFilterClick = { category ->
                    println("Filtrar por categoría: $category")
                    // Aquí puedes manejar la lógica de filtrado
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                contentPadding = PaddingValues(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(products) { plant ->
                    PlantCard(
                        plant = plant,
                        onAddToCart = { println("Agregado al carrito: ${plant.name}") },
                        onVote = { println("Votado: ${plant.name}") }
                    )
                }
            }
        }
    }
}

@Composable
fun FilterSection(
    onFilterClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        FilterIcon(icon = Icons.Filled.Home, label = "Árboles", onClick = { onFilterClick("Árboles") })
        FilterIcon(icon = Icons.Filled.Favorite, label = "Flores", onClick = { onFilterClick("Flores") })
    }
}


@Composable
fun FilterIcon(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier
                .size(50.dp)
                .padding(5.dp),
            tint = Color(0xFF4CAF50)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    ProductList(
        products = samplePlants(),
        onNavigateBack = {},
        onNavigateToDetail = {}
    )
}
