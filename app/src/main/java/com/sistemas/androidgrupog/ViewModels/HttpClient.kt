package com.sistemas.androidgrupog.ViewModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class HttpCLient  {

    suspend fun makeHttpRequest(emailText:String, password:String): Response
    {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();
        val backendAddress = "192.168.1.8:8000"
        val loginPath = "/api/userLogin"

        val json = """
            {
                "email": "$emailText",
                "password": "$password"
            }
        """.trimIndent()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("http://$backendAddress$loginPath")
            .post(requestBody)
            .addHeader("Accept","application/json")
            .build()
        return withContext(Dispatchers.IO){
            client.newCall(request).execute()
        }


    }
}