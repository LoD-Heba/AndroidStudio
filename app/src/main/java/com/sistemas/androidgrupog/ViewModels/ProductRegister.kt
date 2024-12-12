package com.sistemas.androidgrupog.ViewModels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.net.ConnectException

class ProductRegister : ViewModel() {
    val nombreErrorState = mutableStateOf(false)
    val precioErrorState = mutableStateOf(false)

    val nombreErrorMessage = mutableStateOf("")
    val precioErrorMessage = mutableStateOf("")

    val categoryList = mutableListOf<Pair<String, String>>()
    /*
    for (i in 1..5) {
    options.add("Opción $i" to i)
}
}
    * */

    val productUrlImage = mutableStateOf(Uri.parse(""))
    fun runSetCategoria(coroutineScope: CoroutineScope, task: suspend () -> Unit) {
        coroutineScope.launch {
            try {
                task.invoke()
            } catch (e: ConnectException) {
                val test=0
            } catch (e: IOException) {
                val test=0
            } catch (e: Exception) {
                val test=0
            }
        }

    }
    fun runGetCategory (coroutineScope: CoroutineScope, task:suspend () -> Unit){
        coroutineScope.launch {
            try {
                task.invoke()
            } catch (e: ConnectException) {
            } catch (e: IOException) {
            } catch (e: Exception) {
            }
        }
    }
    suspend fun runGetCategory(): Response
    {
        val httpClient = HttpCLient()
        return httpClient.getCategoryHttpRequest()
    }
    suspend fun runHttpSetCategoria(
        categoryName: String,
        categoryUrlImage: Uri,
        categoryId: String,
        context: Context
    ): Response {
        val httpClient = HttpCLient()
        return httpClient.setCategoriaHttpRequest(/*token,*/categoryName,categoryUrlImage,categoryId,context)
    }
    fun procesarRespuesta(response:Response, navController: NavHostController)
    {
        val jsonString = response.body?.string() ?: ""
        val json = JSONObject(jsonString)
        val jsonBodyString = json.getJSONObject("body")
        if (response.code == 201)
        {
            navController.navigate("product_list")
        }
    }
    fun procesarListaCategorias(response: Response)
    {
        //options.add("Opción $i" to i)
        val jsonString = response.body?.string() ?: ""
        val json = JSONObject(jsonString)
        val jsonBodyString = json.getJSONArray("body")
        if (response.code == 200)
        {
            categoryList.clear()
            for (i in 0 until jsonBodyString.length()) {
                val jsonObject = jsonBodyString.getJSONObject(i) // Obtiene cada JSONObject
                val id = jsonObject.getString("id") // Extrae el campo "id"
                val name = jsonObject.getString("nombre") // Extrae el campo "name"
                categoryList.add( name to id)

            }

        }

    }
}