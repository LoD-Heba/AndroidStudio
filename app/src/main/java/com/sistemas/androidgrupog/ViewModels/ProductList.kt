package com.sistemas.androidgrupog.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sistemas.androidgrupog.Models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.net.ConnectException

class ProductList: ViewModel() {
    val productList = mutableStateOf<List<Product>>(emptyList())
    val visible = mutableStateOf(false)

    fun runGetProducts (coroutineScope: CoroutineScope, task:suspend () -> Unit){
        coroutineScope.launch {
            try {
                task.invoke()
            } catch (e: ConnectException) {
            } catch (e: IOException) {
            } catch (e: Exception) {
            }
        }
    }
    suspend fun runGetProducts(): Response
    {
        val httpClient = HttpCLient()
        return httpClient.getProductsHttpRequest()
    }

    fun procesarProductos(response: Response) {
        val jsonString = response.body?.string() ?: ""
        val jsonArray = JSONObject(jsonString).getJSONArray("body")
        val products = mutableListOf<Product>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val product = Product(
                id = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                price = jsonObject.getDouble("price"),
                createdAt = jsonObject.getString("created_at"),
                updatedAt = jsonObject.getString("updated_at"),
                categoryId = jsonObject.getInt("category_id"),
                urlImage = jsonObject.getString("url_image")
            )
            products.add(product)
        }
        productList.value = products

    }
}