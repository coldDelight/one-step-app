package com.colddelight.routine.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.colddelight.routine.RoutineScreen

fun NavController.navigateToRoutine(navOptions: NavOptions? = null) {
    this.navigate(RoutineRoute.route, navOptions)
}

fun NavGraphBuilder.routineScreen(
) {
    composable(route = RoutineRoute.route) {
        RoutineScreen()
    }
}

object RoutineRoute {
    const val route:String = "routine"
}