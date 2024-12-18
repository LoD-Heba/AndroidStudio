package com.sistemas.androidgrupog.Views

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sistemas.androidgrupog.R
import com.sistemas.androidgrupog.ViewModels.Login
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.sistemas.androidgrupog.Navagation.MainDestination

@SuppressLint("SuspiciousIndentation")
@Composable
fun Login(navController:NavHostController) {
    //
    var emailText by remember { mutableStateOf("")}
    var passwordText by remember { mutableStateOf("")}
    //
    //Esta variable conecta con viewmodels.login
    val viewModel = viewModel<Login>()
    //variables reactivas
    var emailErrorMessage by remember { viewModel.emailErrorMessage }
    var passwordErrorMessage by remember { viewModel.passwordErrorMessage }
    var emailErrorState by remember { viewModel.emailErrorState }
    var passwordErrorState by remember { viewModel.passwordErrorState }
    //
    val coroutineScope = rememberCoroutineScope()
    //
    val context = LocalContext.current
    //
   // var visible by remember { mutableStateOf(false) }

    //Este codigo es para añadir animacion en cierto lapso de tiempo, en este caso 1 segundo
   // LaunchedEffect(Unit) {
 //       kotlinx.coroutines.delay(1000)
  //      visible=true
 //   }
        Box(
            modifier = Modifier.fillMaxSize(), //tamaño maximo de la pantalla
            contentAlignment = Alignment.Center //centrea el contenido
        )
        {
            Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = "Fondo",
                    contentScale = ContentScale.Crop, // Ajusta la escala según tu preferencia
                    modifier = Modifier
                        .fillMaxSize() //tamaño
                        //.clip(RoundedCornerShape(16.dp)) esquinas redondeadas

            )
            ////////El contenido
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.7f) // 70% de opacidad
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp

                ),
                modifier = Modifier //comparable a css
                    .wrapContentSize() //adecua el tamaño
                    .padding(25.dp,180.dp)
            ) {
    //////
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Imagen del Logo
                    Image(
                        painter = painterResource(R.drawable.logoclub),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(120.dp,114.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    // Campo de correo electrónico
                    OutlinedTextField(
                    value = emailText,
                    onValueChange = { emailText = it },
                    label = { Text("Email") },
                    placeholder = { Text("Ingrese su correo") },
                    isError = emailErrorState,
                    modifier = Modifier.fillMaxWidth()
                    )

                    if (emailErrorState) {
                        Text(
                            text = emailErrorMessage,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de contraseña
                    OutlinedTextField(
                        value = passwordText,
                        onValueChange = { passwordText = it },
                        label = { Text("Password") },
                        placeholder = { Text("Ingrese su contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón de "¿Olvidaste tu contraseña?"
                    TextButton(
                        onClick = { /* Lógica para restablecer contraseña */ },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = "¿Olvidaste tu contraseña?",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Botones de inicio de sesión y registro
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            // Ejecutar el proceso de login
                            viewModel.runLogin(coroutineScope) {
                                val response = viewModel.runHttpLogin(emailText, passwordText)
                                withContext(Dispatchers.Main) {
                                    viewModel.procesarRespuesta(response, context)
                                    if (viewModel.loginSuccess.value) {
                                        navController.navigate("index") // Navegar solo si el login fue exitoso
                                    }
                                }
                            }
                        }) {
                            Text("Iniciar sesión")
                        }

                    }
                        Button(onClick = { navController.navigate(MainDestination.USER_REGISTER) }) {
                            Text("Registrarse")
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                }

            }
        }


//}
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    // Crear un controlador de navegación para pruebas
    val navController = rememberNavController()
    Login(navController = navController)
}