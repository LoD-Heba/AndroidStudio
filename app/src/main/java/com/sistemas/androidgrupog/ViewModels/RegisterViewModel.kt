package com.sistemas.androidgrupog.ViewModels
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class RegisterViewModel {

    class RegisterViewModel : ViewModel() {

        fun runRegister(
            name: String,
            email: String,
            password: String,
            onSuccess: () -> Unit) {
            viewModelScope.launch  {
                try {
                    val response = makeRegisterRequest(name, email, password)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            onSuccess()
                        } else {
                            // Manejar errores
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        onSuccess()// Manejo de excepciones (conexión fallida, etc.)
                    }
                }
            }
        }

        private fun makeRegisterRequest(name: String, email: String, password: String): okhttp3.Response {
            val client = OkHttpClient()
            val requestBody = FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("password", password)
                .build()
            val request = Request.Builder()
                .url("https://tu-api.com/api/register")
                .post(requestBody)
                .build()
            return client.newCall(request).execute()
        }
    }
}
