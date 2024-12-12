package com.sistemas.androidgrupog.Views

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.sistemas.androidgrupog.ViewModels.ProductRegister
import com.sistemas.ejemplo2.Views.ImagePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductRegister(navController: NavHostController,context:Context){
    val viewModel = viewModel<ProductRegister>()
    val coroutineScope = rememberCoroutineScope()
    var productUrlImage = remember{viewModel.productUrlImage}
    var nombre by remember { mutableStateOf("")}
    //var nombreCategoria by remember { mutableStateOf("")}



    var nombreErrorState = remember{ viewModel.nombreErrorState}

    var categoryList = remember { viewModel.categoryList }

    val options: List<String> = listOf("Opción 1", "Opción 2", "Opción 3", "Opción 4", "Opción 5")
    var expanded by remember { mutableStateOf(false) }
    val textFieldState = rememberTextFieldState(options[0])
    LaunchedEffect(Unit) {
        viewModel.runGetCategory(coroutineScope) {
            val response = viewModel.runGetCategory()
            withContext(Dispatchers.Main) {
                viewModel.procesarListaCategorias(response)
            }
        }
    }

    var selectedCategoryId by remember { mutableStateOf(categoryList.firstOrNull()?.first) }
    var nombreCategoria by remember { mutableStateOf(if(categoryList.isEmpty()) "" else categoryList.first().second) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF00B8D4)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 40.dp,
                    end= 40.dp,
                    top = 40.dp,
                    bottom = 40.dp
                )
                .size(width = 240.dp, height = 100.dp)
        )
        {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            {

                ImagePicker(
                    onImageSelected = { uri ->
                        productUrlImage.value = uri
                    },
                    onImageRemoved = {
                        // Haz algo cuando se elimina la imagen (si es necesario)
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = nombre ,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre Producto") },

                    placeholder = { Text( "Ingrese el nombre")},
                    isError = nombreErrorState.value
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    OutlinedTextField(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .menuAnchor(),
                        value = nombreCategoria,
                        onValueChange = { nombreCategoria = it },
                        label = { Text("Categoria") },

                        placeholder = { Text( "Ingrese el nombre")},
                        isError = nombreErrorState.value
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        categoryList.forEach { (name, id) ->
                            DropdownMenuItem(
                                text = { Text(name, style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    nombreCategoria = name  // Actualizamos el nombre, no el id
                                    selectedCategoryId = id  // Mantenemos el 'id' para la lógica
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            )
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    onClick = {
                        viewModel.runSetCategoria(coroutineScope) {
                            val response = viewModel.runHttpSetCategoria(nombre,productUrlImage.value,selectedCategoryId.toString(),context)
                            withContext(Dispatchers.Main) {
                                viewModel.procesarRespuesta(response,navController)
                            }
                        }
                    }) {
                    Text(
                        text = "Ingresar"
                    )
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
    ProductRegister(navController,context)
}