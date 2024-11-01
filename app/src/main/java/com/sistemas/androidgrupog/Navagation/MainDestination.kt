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

object MainDestination {
    const val LOGIN = "login"
    const val MAIN = "main"
}

@Composable
fun AppNavigation(navController: NavHostController,activity:ComponentActivity){

    NavHost(navController, startDestination = MainDestination.LOGIN) {
        composable(MainDestination.LOGIN){
            Login()
        }
        composable(MainDestination.MAIN){
            Main()
        }
    }
}
