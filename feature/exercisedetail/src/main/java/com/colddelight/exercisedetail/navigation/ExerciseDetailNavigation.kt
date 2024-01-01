package com.colddelight.exercisedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.colddelight.exercisedetail.ExerciseDetailScreen

fun NavController.navigateExerciseToExerciseDetail() {
    navigate(ExerciseDetailRoute.route)
}

fun NavGraphBuilder.exerciseDetailScreen(
) {
    composable(route = ExerciseDetailRoute.route) {
        ExerciseDetailScreen()
    }
}

object ExerciseDetailRoute {
    const val route: String = "exercise_detail"
}