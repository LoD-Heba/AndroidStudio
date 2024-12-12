package com.sistemas.androidgrupog.Views

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
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000)
        visible=true
    }
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = tween(
                durationMillis = 1500, // Duración de 1 segundo (1000 ms)
                easing = LinearOutSlowInEasing // Interpolador tipo "ease"
            ),
            transformOrigin = TransformOrigin.Center
        ) + expandIn(expandFrom = Alignment.Center)
        /*fadeIn(
            // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
            initialAlpha = 0f, // Empieza desde completamente transparente
            animationSpec = tween(
                durationMillis = 10000, // Duración de 1 segundo (1000 ms)
                easing = LinearOutSlowInEasing // Interpolador tipo "ease"
            )
        )*/
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF34495E)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 90.dp
                ),
                modifier = Modifier.wrapContentSize()
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
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(23.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {

                        Image(
                            modifier = Modifier.size(80.dp, 80.dp)
                                .clip(RoundedCornerShape(40.dp)),
                            painter = painterResource(R.drawable.paraandroid),
                            contentDescription = "Probando imagen",
                            contentScale = ContentScale.Crop
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
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
        }
    }

}
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    // Crear un controlador de navegación para pruebas
    val navController = rememberNavController()
    Login(navController = navController)
}