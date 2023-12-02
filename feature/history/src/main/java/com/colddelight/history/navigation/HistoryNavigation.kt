package com.colddelight.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.colddelight.history.HistoryScreen

fun NavController.navigateToHistory(navOptions: NavOptions? = null) {
    this.navigate(HistoryRoute.route, navOptions)
}

fun NavGraphBuilder.historyScreen(
) {
    composable(route = HistoryRoute.route) {
        HistoryScreen()
    }
}

object HistoryRoute {
    const val route:String = "history"
}