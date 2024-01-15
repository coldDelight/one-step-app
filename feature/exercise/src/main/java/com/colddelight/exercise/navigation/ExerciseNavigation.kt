package com.colddelight.exercise.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.colddelight.exercise.ExerciseScreen


fun NavGraphBuilder.exerciseScreen(
    onDetailButtonClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    composable(route = ExerciseRoute.route) {
        ExerciseScreen(onDetailButtonClick = onDetailButtonClick, onFinishClick = onFinishClick)
    }
}
object ExerciseRoute {
    const val route: String = "exercise"

}