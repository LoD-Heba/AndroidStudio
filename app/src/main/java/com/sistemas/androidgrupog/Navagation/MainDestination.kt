package com.sistemas.androidgrupog.Navagation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sistemas.androidgrupog.Views.Login
import com.sistemas.androidgrupog.Views.Main
import com.sistemas.androidgrupog.Views.ProductList
import com.sistemas.androidgrupog.Views.ProductRegister

object MainDestination {
    const val LOGIN = "login"
    const val MAIN = "main"
    const val PRODUCT_REGISTER = "product_register"
    const val PRODUCT_LIST = "product_list"
}

@Composable
fun AppNavigation(navController: NavHostController,activity:ComponentActivity){

    NavHost(navController, startDestination = MainDestination.PRODUCT_REGISTER) {
        composable(MainDestination.LOGIN){
            Login(navController)
        }
        composable(MainDestination.MAIN){
            Main()
        }
        composable(MainDestination.PRODUCT_REGISTER){
            ProductRegister(navController,activity)
        }
        composable(MainDestination.PRODUCT_LIST){
            ProductList(navController,activity)
        }
    }
}
