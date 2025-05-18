package com.example.f1tracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.f1tracker.ui.screens.ScoreBoard
import com.example.f1tracker.ui.screens.DriverDetailsScreen
import f1tracker.ui.screens.AuthScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "auth") {
        composable("home") {
            ScoreBoard(navController = navController)
        }
        composable("driver/{driverNumber}") { backStackEntry ->
            val driverNumber = backStackEntry.arguments?.getString("driverNumber")?.toIntOrNull()
            if (driverNumber != null) {
                DriverDetailsScreen(driverNumber, navController = navController)
            }
        }
        composable("auth") {
            AuthScreen(navController = navController)
        }
    }
}