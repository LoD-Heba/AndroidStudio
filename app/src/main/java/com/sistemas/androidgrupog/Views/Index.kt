package com.sistemas.androidgrupog.Views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import com.sistemas.androidgrupog.R

@Composable
fun Index() {
    val productCount = 4 // TODO: Obtener valor dinámico desde el backend usando OkHttp y Laravel

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF5E2))
            .padding(top = 20.dp)

    ) {
        TopBar(productCount)
        SearchSection()
        FeaturedPlantsSection(samplePlants())
        NewPlantsSection(onViewAllClick = {})
        BottomNavigationBar(
                onHomeClick = { println("Navegar a Home") },
                onSearchClick = { println("Navegar a lista de productos") },
                onFavoritesClick = { println("Navegar a Favoritos") },
                onProfileClick = { println("Navegar a Perfil") }
        )

    }

}


// Data class para representar una planta
data class Plant(
    val id: Int,
    val name: String,
    val price: String,
    val imageRes: Int, // TODO: Reemplazar con URL si se utiliza backend
    val votes: Int
)
// Función de ejemplo para datos simulados (temporal)
fun samplePlants(): List<Plant> {
    return listOf(
        Plant(1, "Aloe Vera", "25.99$", R.drawable.logoclub, 10),
        Plant(2, "Cactus", "15.50$", R.drawable.logoclub, 5),
        Plant(3, "Orquídea", "30.00$", R.drawable.logoclub, 7)
    )

}


@Composable
fun TopBar(productCount: Int) {
    var menuExpanded by remember { mutableStateOf(false) }
    //val rotation by animateFloatAsState(if (menuExpanded) 90f else 0f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                start = 10.dp,
                end = 10.dp,
                bottom = 5.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logoclub),
            contentDescription = "Logo",
            modifier = Modifier.size(70.dp)
        )

            Box(contentAlignment = Alignment.TopEnd) {
                IconButton(onClick = { /* TODO: Navegación al carrito */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.cart),
                        contentDescription = "Carrito",
                        tint = Color(0xFF588157),
                        modifier = Modifier.size(35.dp)
                    )
                }
                if (productCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color.Red, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = productCount.toString(),
                            color = Color.White,
                            fontSize = 13.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }


@Composable
fun SearchSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 5.dp,
                start = 10.dp,
                end = 10.dp,
                bottom = 5.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally // Centra el contenido
    ) {
        // Texto centrado y con más estilo
        Text(
            text = "Encuentra la planta ideal para tu hogar.",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D6A4F), // Verde oscuro estilizado
            modifier = Modifier
                .padding(bottom = 20.dp, top = 5.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        // Campo de búsqueda
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("¿Qué planta buscas?", color = Color.Gray) },
            trailingIcon = { // Lupa al final
                IconButton(onClick = { /* TODO: Acción de búsqueda */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.lup),
                        contentDescription = "Buscar",
                        tint = Color(0xFF52796F), // Color de la lupa
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFF74C69D), RoundedCornerShape(12.dp)), // Borde verde claro
            shape = RoundedCornerShape(12.dp)
        )
    }
}


@Composable
fun FeaturedPlantsSection(plants: List<Plant>) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(
            top = 5.dp,
            start = 10.dp,
            end = 10.dp,
            bottom = 5.dp
        )) {

        Text(
            text = "Plantas destacadas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF344E41)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // LazyRow para deslizar horizontalmente
        LazyRow {
            // Máximo 10 tarjetas de plantas
            items(plants.take(10)) { plant ->
                PlantCard(
                    plant = plant,
                    onAddToCart = { /* TODO: Implementar lógica para agregar al carrito */ },
                    onVote = { /* TODO: Implementar lógica para votar */ }
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}

// Card individual para cada planta
@Composable
fun PlantCard(plant: Plant, onAddToCart: () -> Unit, onVote: () -> Unit) {
    var voteCount by remember { mutableStateOf(plant.votes) }
    var clicked by remember { mutableStateOf(false) }
    val visibility by remember { derivedStateOf { clicked } }

    Box(
        modifier = Modifier
            .width(170.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(5.dp)

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = plant.imageRes),
                contentDescription = plant.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = plant.name, fontWeight = FontWeight.Medium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(plant.price, fontSize = 15.sp, color = Color.Gray)
                IconButton(onClick = onAddToCart) {
                    Icon(
                        painter = painterResource(id = R.drawable.cart),
                        contentDescription = "Carrito",
                        tint = Color(0xFF74C69D),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        // Votación con animación
        AnimatedVisibility(visible = true) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF74C69D))
                    .clickable {
                        clicked = !clicked
                        voteCount++
                        onVote()
                    }
                    .padding(6.dp)
            ) {
                Text("★ $voteCount", color = Color.White, fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun NewPlantsSection(onViewAllClick: () -> Unit)
{
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(
            top = 1.dp,
            start = 10.dp,
            end = 10.dp,
            bottom = 5.dp
        )) {
        // Título y botón "Ver todos"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Nuevos Productos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = onViewAllClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = "Ver todos",
                    color = Color(0xFF74C69D),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de productos limitada a 10
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(samplePlants().take(10)) { plant -> // Limita a 10 productos
                PlantCard(
                    plant = plant,
                    onAddToCart = {},
                    onVote = {}
                )
            }
        }
    }
}
////////////////////////////////////
@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),

        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón Home
            IconButton(onClick = onHomeClick) {
                Icon(
                    painter = painterResource(id = R.drawable.house_solid),
                    contentDescription = "Home",
                    tint = Color(0xFF74C69D),
                    modifier = Modifier.size(30.dp)
                )
            }

            // Botón Buscar
            IconButton(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(id = R.drawable.store_solid),
                    contentDescription = "Store",
                    tint = Color(0xFF74C69D),
                    modifier = Modifier.size(30.dp)
                )
            }

            // Botón Favoritos
            IconButton(onClick = onFavoritesClick) {
                Icon(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = "card",
                    tint = Color(0xFF74C69D),
                    modifier = Modifier.size(30.dp)
                )
            }
            // Botón Perfil
            IconButton(onClick = onProfileClick) {
                Icon(
                    painter = painterResource(id = R.drawable.user_large_solid),
                    contentDescription = "Profile",
                    tint = Color(0xFF74C69D),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}



