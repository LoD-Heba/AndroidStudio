package com.sistemas.androidgrupog.ViewModels
//controller

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Response
import okio.IOException
import org.json.JSONObject
import java.net.ConnectException

class Login : ViewModel() {
    // Mensajes de error
    val emailErrorMessage = mutableStateOf("")
    val passwordErrorMessage = mutableStateOf("")
    val emailErrorState = mutableStateOf(false)
    val passwordErrorState = mutableStateOf(false)

    // Estado del login
    val loginSuccess = mutableStateOf(false)

    // Métodos: coroutineScope, hilos o procesos en segundo plano--- asincrónicos
    fun runLogin(coroutineScope: CoroutineScope, task: suspend () -> Unit) {
        coroutineScope.launch {
            try {
                task.invoke()
            } catch (e: ConnectException) {
                // Manejar error de conexión
            } catch (e: IOException) {
                // Manejar error de IO
            } catch (e: Exception) {
                // Manejar otros errores
            }
        }
    }

    // Suspend: método que se ejecuta solo en segundo plano
    suspend fun runHttpLogin(emailText: String, password: String): Response {
        val httpClient = HttpCLient() // Crear instancia
        return httpClient.makeHttpRequest(emailText, password) // Ejecutar la solicitud
    }

    ///////////////////
    fun procesarRespuesta(response: Response, context: Context) {
        if (response.code == 200) {
            loginSuccess.value = true // Login exitoso
        } else if (response.code == 422) {
            loginSuccess.value = false // Fallo en el login
            val body = response.body
            val stringResponse = body?.string() ?: ""
            val jsonResponse = JSONObject(stringResponse)
            val jsonArrayBody = jsonResponse.getJSONArray("body")
            val jsonErrors = jsonArrayBody.getJSONObject(0)
            if (jsonErrors.has("email")) {
                emailErrorState.value = true
                emailErrorMessage.value = jsonErrors.getJSONArray("email")[0].toString()
            } else {
                emailErrorState.value = false
            }
        } else {
            loginSuccess.value = false // Otros errores
        }
    }
    ///////////////////
}
