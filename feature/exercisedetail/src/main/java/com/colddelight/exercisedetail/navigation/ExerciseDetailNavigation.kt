package com.colddelight.exercisedetail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
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
        val viewModelStoreOwner = LocalViewModelStoreOwner.current
        val nonNullViewModelStoreOwner = requireNotNull(viewModelStoreOwner)

        ExerciseDetailScreen(
            viewModel = hiltViewModel(nonNullViewModelStoreOwner)
        )
    }
}

object ExerciseDetailRoute {
    const val route: String = "exercise_detail"
}