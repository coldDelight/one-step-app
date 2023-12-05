package com.colddelight.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.colddelight.exercise.ExerciseScreen
import com.colddelight.exercise.navigation.ExerciseRoute
import com.colddelight.home.HomeScreen

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HomeRoute.route, navOptions)
}

fun NavController.navigateHomeToExercise(routineDayId: Int) {
    navigate(ExerciseRoute.detailRoute(routineDayId = routineDayId))
}

fun NavGraphBuilder.homeScreen(
    onStartButtonClick: (Int) -> Unit
) {
    composable(route = HomeRoute.route) {
        HomeScreen(onStartButtonClick = onStartButtonClick)
    }

    composable(
        route = ExerciseRoute.detailRoute(id),
    ) {
        ExerciseScreen()
    }
}

object HomeRoute {
    const val route:String = "home"
}