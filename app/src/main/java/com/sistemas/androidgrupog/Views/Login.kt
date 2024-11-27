package com.sistemas.androidgrupog.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sistemas.androidgrupog.R
import com.sistemas.androidgrupog.ViewModels.Login
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController:NavHostController) {
    var emailText by remember { mutableStateOf("")}
    var passwordText by remember { mutableStateOf("")}

    val viewModel = viewModel<Login>()
    var emailErrorMessage by remember { viewModel.emailErrorMessage }
    var passwordErrorMessage by remember { viewModel.passwordErrorMessage }
    var emailErrorState by remember { viewModel.emailErrorState }
    var passwordErrorState by remember { viewModel.passwordErrorState }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF34495E)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier.size(width = 380.dp, height = 390.dp)
                .fillMaxSize()
                .padding(
                    start = 40.dp,
                    end = 40.dp,
                    top= 30.dp,
                    bottom = 20.dp
                )
        ) {
            Column(
                modifier = Modifier.padding(
                    25.dp
                )
            ) {
                Row {
                    Image(
                        modifier = Modifier.size(80.dp,80.dp),
                        painter = painterResource(R.drawable.paraandroid),
                        contentDescription = "Probando imagen"
                    )
                }
                Row {
                    OutlinedTextField(
                        value = emailText,
                        onValueChange = { emailText = it },
                        label = { Text("Email")},
                        placeholder = { Text("Ingrese su correo")},
                        isError = emailErrorState
                    )
                }
                if(emailErrorState)
                {
                    Row {
                        Text(
                            text=emailErrorMessage
                        )
                    }
                }
                Row {
                    OutlinedTextField(
                        value = passwordText,
                        onValueChange = { passwordText = it },
                        label = { Text("Password")},
                        placeholder = { Text("Ingrese su Constraseña")}
                    )
                }
                Row {
                    Button(onClick = {
                        viewModel.runLogin(coroutineScope){
                            val response = viewModel.runHttpLogin(emailText,passwordText)
                            withContext(Dispatchers.Main){
                                viewModel.procesarRespuesta(response, context, navController)
                            }
                        }
                    }) {
                        Text("Ingresar")
                    }
                }
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.epico),
            contentDescription = "Fondo de tarjeta",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        )

        // Tarjeta encima de la imagen
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .size(width = 380.dp, height = 460.dp)
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x2a27ea).copy(alpha = 0.1f)
            )
        ) {
            Column(
                modifier = Modifier.padding(25.dp),

                ) {

                Row {
                    OutlinedTextField(
                        value = emailText,
                        onValueChange = { emailText = it },
                        label = { Text("Email") },
                        placeholder = { Text("Ingrese su correo",
                            //color = Color.White,
                            //fontWeight = FontWeight.Bold
                            )},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledPlaceholderColor = Color.Blue,
                        )
                    )
                }
                Row {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("Password") },
                        placeholder = { Text("Ingrese su Contraseña") },

                        )
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(
                            onClick = { navController.navigate("main") },
                            modifier = Modifier.size(width = 130.dp, height = 40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6200EE),
                                contentColor = Color.White
                            )
                        )
                        {
                            Text("Ingresar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { navController.navigate("main") },
                            modifier = Modifier.size(width = 130.dp, height = 40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6200EE),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Registrarse")
                        }
                    }
                }
            }
        }
    }

}