package com.sistemas.androidgrupog.ViewModels

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class HttpCLient  {
//conexion con laravel userLogin
    ////////////////////////////////////////
    suspend fun makeHttpRequest(emailText: String, password: String): Response {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val backendAddress = "http://192.168.1.133:8000"  // Definir la base URL correctamente
        val loginPath = "/api/userLogin"  // Definir el path específico

        // Preparar los datos del login en formato JSON
        val json = """
        {
            "email": "$emailText",
            "password": "$password"
        }
    """.trimIndent()

        // Definir el tipo de contenido para la solicitud
        val mediaType = "application/json; charset=utf-8".toMediaType()

        // Crear el cuerpo de la solicitud POST
        val requestBody = json.toRequestBody(mediaType)

        // Crear la solicitud HTTP con la URL completa
        val request = Request.Builder()
            .url("$backendAddress$loginPath")  // Concatenar la URL base con el path
            .post(requestBody)
            .addHeader("Accept", "application/json")
            .build()

        // Ejecutar la solicitud de forma asíncrona usando withContext para no bloquear el hilo principal
        return withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }
    }

    //////////////////////////////////////////////////////////
    suspend fun setCategoriaHttpRequest(/*token: String?,*/ categoryName: String, imageUrl: Uri?,categoryId:String, context: Context): Response {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val backendAddress = "192.168.1.133:8000"
        val productPath = "/api/product"

        // Crear el cuerpo de la solicitud como multipart/form-data
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", categoryName)
            .addFormDataPart("price", "23")
            .addFormDataPart("category_id", categoryId)

        imageUrl?.let { uri ->
            val contentResolver: ContentResolver = context.contentResolver

            // Obtener el nombre del archivo y el tipo de contenido
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val mimeTypeIndex = it.getColumnIndex("mime_type")

                if (it.moveToFirst()) {
                    val fileName = it.getString(nameIndex)
                    val mimeType = it.getString(mimeTypeIndex)

                    // Obtener InputStream desde la URI
                    contentResolver.openInputStream(uri)?.use { inputStream ->
                        // Crear la parte del formulario con el InputStream
                        val filePart = inputStream.readBytes().toRequestBody(mimeType?.toMediaType())
                        requestBody
                            .addFormDataPart("url_image", fileName, filePart)
                            .build()

                        // Aquí puedes enviar tu solicitud HTTP con Retrofit utilizando "requestBody"
                    }
                }
            }
        }

        // Construir la solicitud
        val request = Request.Builder()
            .url("http://$backendAddress$productPath")
            .post(requestBody.build())
            //.addHeader("Authorization", "Bearer $token")
            .build()

        // Realizar la solicitud y devolver la respuesta
        return withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }
    }

    //Fin Category

    //
    suspend fun getProductsHttpRequest():Response
    {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val backendAddress = "192.168.1.133:8000"
        val loginPath = "/api/product"


// Construye la URL con los parámetros
        val url = "http://$backendAddress$loginPath"

        val request = Request.Builder()
            .url(url)
            .get() // Método GET
            .addHeader("Accept", "application/json")
            .build()

        return withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }
    }

    suspend fun getCategoryHttpRequest():Response
    {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val backendAddress = "192.168.1.133:8000"
        val loginPath = "/api/category"


// Construye la URL con los parámetros
        val url = "http://$backendAddress$loginPath"

        val request = Request.Builder()
            .url(url)
            .get() // Método GET
            .addHeader("Accept", "application/json")
            .build()

        return withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }
    }
}