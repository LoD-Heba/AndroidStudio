package com.sistemas.androidgrupog.Navagation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sistemas.androidgrupog.ViewModels.ProductList
import com.sistemas.androidgrupog.Views.Login
import com.sistemas.androidgrupog.Views.Index
import com.sistemas.androidgrupog.Views.ProductList
import com.sistemas.androidgrupog.Views.ProductRegister
import com.sistemas.androidgrupog.Views.samplePlants

object MainDestination {
    const val LOGIN = "login"
    const val INDEX = "index"
    const val PRODUCT_REGISTER = "product_register"
    const val PRODUCT_LIST = "product_list"
}

@Composable
fun AppNavigation(navController: NavHostController, activity: ComponentActivity) {
    // Destino principal de la app
    NavHost(navController, startDestination = MainDestination.INDEX) {
        composable(MainDestination.LOGIN) {
            Login(navController)
        }

        composable(MainDestination.INDEX) {
            Index(navController)  // Pasar el navController a Index
        }

        // Aquí pasa los parámetros necesarios para ProductList
        composable(MainDestination.PRODUCT_LIST) {
            // Pasa productos y la cantidad de productos en el carrito
            ProductList(
                products = samplePlants(),  // Debes crear esta función o lista
                navController = navController,
                productCount = 5 // O la cantidad real de productos
            )
        }

        composable(MainDestination.PRODUCT_REGISTER) {
            ProductRegister(navController, activity)
        }
    }
}


