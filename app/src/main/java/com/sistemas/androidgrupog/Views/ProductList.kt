package com.sistemas.androidgrupog.Views

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sistemas.androidgrupog.Navagation.MainDestination
import com.sistemas.androidgrupog.R

@Composable
fun ProductList(
    products: List<Plant>,
    navController: NavHostController,
    productCount: Int // Puedes pasar el conteo de productos aquí
) {
    Scaffold(
        topBar = {
            TopBar(productCount = productCount) // Agregamos el TopBar
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF5E2))
                .padding(innerPadding) // Ajustar contenido para no superponer la barra
        ) {
            // Nueva sección de filtros con imágenes
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
                        onVote = { println("Votado: ${plant.name}") },
                        // onNavigateToDetail = { /* Aquí se puede manejar la navegación al detalle del producto */ }
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
        FilterImage(imageRes = R.drawable.arboles, label = "Árboles", onClick = { onFilterClick("Árboles") })
        FilterImage(imageRes = R.drawable.flores, label = "Flores", onClick = { onFilterClick("Flores") })
        FilterImage(imageRes = R.drawable.exterior, label = "Exterior", onClick = { onFilterClick("Exterior") })
        FilterImage(imageRes = R.drawable.interior, label = "Interior", onClick = { onFilterClick("Interior") })
    }
}

@Composable
fun FilterImage(
    imageRes: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        // Modificamos el color de la imagen usando el filtro 'ColorFilter'
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            modifier = Modifier
                .size(50.dp), // Ajusta el tamaño de las imágenes
            colorFilter = ColorFilter.tint(Color(0xFF74C69D)) // Aplica el color verde
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    ProductList(
        products = samplePlants(),
        navController = rememberNavController(),
        productCount = 5 // Valor de ejemplo para el conteo de productos en el carrito
    )
}

