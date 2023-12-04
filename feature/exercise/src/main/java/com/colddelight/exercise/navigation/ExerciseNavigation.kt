package com.colddelight.exercise.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.colddelight.exercise.ExerciseScreen


fun NavController.navigateToExercise(navOptions: NavOptions? = null) {
    this.navigate(ExerciseRoute.route, navOptions)
}

fun NavGraphBuilder.exerciseScreen(
) {
    composable(route = ExerciseRoute.detailRoute(1)) {
        ExerciseScreen()
    }
}

object ExerciseRoute {
    const val route:String = "exercise"

    fun detailRoute(routineDayId: Int): String = "$route/$routineDayId"
}