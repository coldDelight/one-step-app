package com.colddelight.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.colddelight.exercise.ExerciseScreen
import com.colddelight.exercise.navigation.ExerciseRoute
import com.colddelight.home.HomeScreen

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HomeRoute.route, navOptions)
}

fun NavController.navigateHomeToExercise() {
    navigate(ExerciseRoute.route)
}

fun NavGraphBuilder.homeScreen(
    onStartButtonClick: () -> Unit,
) {
    composable(route = HomeRoute.route) {
        HomeScreen(onStartButtonClick = onStartButtonClick)
    }
}

object HomeRoute {
    const val route: String = "home"
}