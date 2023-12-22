package com.colddelight.exercisedetail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.colddelight.exercisedetail.ExerciseDetailScreen

fun NavController.navigateExerciseToExerciseDetail(exerciseId: Int) {
    navigate(ExerciseDetailRoute.detailRoute(exerciseId))
}

fun NavGraphBuilder.exerciseDetailScreen(
) {
    composable(route = "${ExerciseDetailRoute.route}/{exerciseId}") {
        val viewModelStoreOwner = LocalViewModelStoreOwner.current
        val nonNullViewModelStoreOwner = requireNotNull(viewModelStoreOwner)

        ExerciseDetailScreen(
            exerciseDetailViewModel = hiltViewModel(nonNullViewModelStoreOwner)
        )
    }
}

object ExerciseDetailRoute {
    const val route: String = "exercise_detail"
    fun detailRoute(exerciseId: Int): String = "$route/$exerciseId"
}