package com.sistemas.androidgrupog.Views

import android.content.ClipData.Item
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.sistemas.androidgrupog.R
import com.sistemas.androidgrupog.ViewModels.ProductList

@Composable
fun ProductList(navController: NavHostController, context: Context){
    // Una lista de datos de ejemplo (puede ser cualquier tipo de lista)
    val viewModel = viewModel<ProductList>()
    val coroutineScope = rememberCoroutineScope()
    val productList by viewModel.productList

    var visible by remember { viewModel.visible }

    var selectedRating by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.runGetProducts(coroutineScope) {
            val response = viewModel.runGetProducts()
            viewModel.procesarProductos(response)
        }
    }
    LaunchedEffect(productList) {
        // Solo activa la visibilidad después de que los productos sean cargados
        if (productList.isNotEmpty()) {
            visible = true
        }
    }

    //val items = listOf("Elemento 1", "Elemento 2", "Elemento 3", "Elemento 4", "Elemento 5", "Elemento 6", "Elemento 7", "Elemento 8")


    Box(
        modifier = Modifier.fillMaxSize()
            .padding(45.dp),
        contentAlignment = Alignment.TopCenter
    ){
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 65.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(productList) { product ->
                Box(modifier = Modifier.size(width = 240.dp, height = 250.dp)){
                    AnimatedVisibility(
                        visible = visible,
                        enter =
                        slideInHorizontally(animationSpec = tween(durationMillis = 2000)) { fullWidth ->
                            // Offsets the content by 1/3 of its width to the left, and slide towards right
                            // Overwrites the default animation with tween for this slide animation.
                            fullWidth / 3
                        } +
                                fadeIn(
                                    // Overwrites the default animation with tween
                                    animationSpec = tween(durationMillis = 2000)
                                ),
                        exit =
                        slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                            // Overwrites the ending position of the slide-out to 200 (pixels) to the right
                            2000
                        } + fadeOut()
                    ) {
                        OutlinedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically)
                                {
                                    Image(
                                        modifier = Modifier.size(10.dp, 100.dp)
                                            .clip(RoundedCornerShape(15.dp)),
                                        painter=rememberAsyncImagePainter("http://192.168.1.8:8000/storage/products/${product.urlImage}"),
                                        contentDescription = "Imagen desde URL",
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Spacer(
                                    modifier = Modifier.size(12.dp)
                                )
                                Row(modifier= Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically)
                                {
                                    HorizontalDivider(
                                        modifier = Modifier.width(200.dp)
                                    )
                                }

                                Text(
                                    text = product.id.toString(),
                                    modifier = Modifier
                                        .padding(8.dp),
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    text = product.name,
                                    modifier = Modifier
                                        .padding(8.dp),
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    text = product.price.toString(),
                                    modifier = Modifier
                                        .padding(8.dp),
                                    textAlign = TextAlign.Center,
                                )
                                Row {
                                    for (i in 1..5) {
                                        Icon(
                                            imageVector = if (i <= selectedRating) Icons.Filled.Star else Icons.Outlined.Star,
                                            contentDescription = null,
                                            tint = if (i <= selectedRating) Color(0xFFFFD700) else Color.Gray,
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clickable { selectedRating = i }
                                        )
                                    }
                                }
                            }

                        }
                    }
                }

                Spacer(
                    modifier = Modifier.size(12.dp)
                )
                HorizontalDivider()
                Spacer(
                    modifier = Modifier.size(12.dp)
                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    ProductList(navController,context)
}