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
    //mensajes de error
    val emailErrorMessage = mutableStateOf("");
    val passwordErrorMessage = mutableStateOf("");
    val emailErrorState = mutableStateOf(false);
    val passwordErrorState = mutableStateOf(false);
    //
    //metodos: coroutineScope, hilos o procesos en segundo plano--- asincron
    fun runLogin(coroutineScope: CoroutineScope, task: suspend ()->Unit){
        coroutineScope.launch {
            try{
                task.invoke()
            } catch (e:ConnectException){
            }
            catch (e:IOException){}
            catch (e:Exception){}
        }
    }
    //

    //suspend: metodo que es ejecutado solo si existe un segundo plano
    //recive parametros: emailText y password
    suspend fun runHttpLogin(emailText:String,password:String): Response {
        val httpClient = HttpCLient() //crear instancia
        return httpClient.makeHttpRequest(emailText,password) //corre el metodo makeHttpRequest
    }
    fun procesarRespuesta(response: Response, context: Context, navController: NavHostController)
    {
        if(response.code == 200)
        {
            val body = response.body
            navController.navigate("product_register")
        }
        if(response.code == 422)
        {
            val body = response.body
            val stringResponse = body?.string() ?: ""
            val jsonResponse = JSONObject(stringResponse)
            val jsonArrayBody = jsonResponse.getJSONArray("body")
            val jsonErrors = jsonArrayBody.getJSONObject(0)
            if(jsonErrors.has("email"))
            {
                emailErrorState.value=true
                emailErrorMessage.value= jsonErrors.getJSONArray("email")[0].toString()
            }
            else{
                emailErrorState.value = false
            }


        }
    }
}
