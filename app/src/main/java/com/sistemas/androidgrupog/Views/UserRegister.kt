package com.sistemas.androidgrupog.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.sistemas.androidgrupog.R


@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit, // Callback para volver al login
    onRegister: (String, String, String) -> Unit // Callback para registrar usuario
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordErrorState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(),//tamaño maximo de la pantalla

        contentAlignment = Alignment.Center //centrea el contenido
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop, // Ajusta la escala según tu preferencia
            modifier = Modifier
                .fillMaxSize() //tamaño
            //.clip(RoundedCornerShape(16.dp)) esquinas redondeadas

        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 160.dp,
                    bottom = 160.dp
                )
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color.White.copy(alpha = 0.7f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Registro de Usuario",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                placeholder = { Text("Ingrese su nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) //añade espacio a los lados
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                placeholder = { Text("Ingrese su correo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp)) //añade espacio a los lados

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                placeholder = { Text("Ingrese su contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(20.dp)) //añade espacio a los lados

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña") },
                placeholder = { Text("Confirme su contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            if (passwordErrorState) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(24.dp)) //añade espacio a los lados

            Button(
                onClick = {
                    if (password == confirmPassword) {
                        onRegister(name, email, password)
                    } else {
                        passwordErrorState = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Registrarse")
            }
            Spacer(modifier = Modifier.height(16.dp)) //añade espacio a los lados

            TextButton(
                onClick = { onBackToLogin() },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    // Crear un controlador de navegación para pruebas
    val navController = rememberNavController()

    RegisterScreen(
        onBackToLogin = { /* Acción ficticia para regresar */ },
        onRegister = { name, email, password ->
            // Acción ficticia para registrar un usuario
        }
    )
}
