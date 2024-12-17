package com.sistemas.androidgrupog.Views

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.sistemas.androidgrupog.R
import com.sistemas.androidgrupog.ViewModels.ProductRegister
import com.sistemas.ejemplo2.Views.ImagePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun TopBa(productCount: Int) {
    // Estado para controlar la expansión del menú
    var menuExpanded by remember { mutableStateOf(false) }

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
        // Logo del club
        Image(
            painter = painterResource(id = R.drawable.logoclub),
            contentDescription = "Logo",
            modifier = Modifier.size(70.dp)
        )

        // Icono del carrito con contador de productos
        Box(contentAlignment = Alignment.TopEnd) {
            IconButton(onClick = { /* TODO: Navegación al carrito */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = "Carrito",
                    tint = Color(0xFF588157),
                    modifier = Modifier.size(35.dp)
                )
            }

            // Mostramos el contador solo si hay productos
            if (productCount > 0) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.Red, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = productCount.toString(),
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductRegister(navController: NavHostController, context: Context) {
    val viewModel = viewModel<ProductRegister>()
    val coroutineScope = rememberCoroutineScope()
    var productUrlImage = remember { viewModel.productUrlImage }
    var nombre by remember { mutableStateOf("") }

    // Estado para manejar errores del nombre del producto
    val nombreErrorState = remember { viewModel.nombreErrorState }

    // Lista de categorías obtenidas del ViewModel
    val categoryList = remember { viewModel.categoryList }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategoryId by remember { mutableStateOf(categoryList.firstOrNull()?.first) }
    var nombreCategoria by remember { mutableStateOf(if (categoryList.isEmpty()) "" else categoryList.first().second) }

    LaunchedEffect(Unit) {
        // Obtener categorías cuando se monta el componente
        viewModel.runGetCategory(coroutineScope) {
            val response = viewModel.runGetCategory()
            withContext(Dispatchers.Main) {
                viewModel.procesarListaCategorias(response)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF00B8D4)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Selector de imagen del producto
                ImagePicker(
                    onImageSelected = { uri ->
                        productUrlImage.value = uri
                    },
                    onImageRemoved = {
                        // Acción opcional al eliminar una imagen
                    }
                )

                // Campo para ingresar el nombre del producto
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre Producto") },
                    placeholder = { Text("Ingrese el nombre") },
                    isError = nombreErrorState.value
                )

                // Menú desplegable para seleccionar la categoría
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = nombreCategoria,
                        onValueChange = { nombreCategoria = it },
                        label = { Text("Categoría") },
                        modifier = Modifier.menuAnchor(),
                        isError = nombreErrorState.value
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categoryList.forEach { (id, name) ->
                            DropdownMenuItem(
                                text = { Text(name) },
                                onClick = {
                                    nombreCategoria = name
                                    selectedCategoryId = id
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Botón para ingresar el producto
                Button(
                    onClick = {
                        viewModel.runSetCategoria(coroutineScope) {
                            val response = viewModel.runHttpSetCategoria(
                                nombre,
                                productUrlImage.value,
                                selectedCategoryId.toString(),
                                context
                            )
                            withContext(Dispatchers.Main) {
                                viewModel.procesarRespuesta(response, navController)
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Ingresar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Column {
        TopBa(productCount = 9) // Simulamos un contador de productos
        ProductRegister(navController, context)
    }
}
