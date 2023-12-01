package com.colddelight.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.colddelight.home.HomeScreen

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HomeRoute.route, navOptions)
}

fun NavGraphBuilder.homeScreen(
) {
    composable(route = HomeRoute.route) {
        HomeScreen()
    }
}

object HomeRoute {
    const val route:String = "home"
}