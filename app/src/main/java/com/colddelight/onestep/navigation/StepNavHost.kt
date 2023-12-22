package com.colddelight.onestep.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.colddelight.exercise.navigation.exerciseScreen
import com.colddelight.exercisedetail.navigation.exerciseDetailScreen
import com.colddelight.exercisedetail.navigation.navigateExerciseToExerciseDetail
import com.colddelight.history.navigation.historyScreen
import com.colddelight.home.navigation.HomeRoute
import com.colddelight.home.navigation.homeScreen
import com.colddelight.home.navigation.navigateHomeToExercise
import com.colddelight.onestep.ui.StepAppState
import com.colddelight.routine.navigation.routineScreen

@Composable
fun StepNavHost(
    appState: StepAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HomeRoute.route,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(onStartButtonClick = navController::navigateHomeToExercise)
        historyScreen()
        routineScreen()
        exerciseScreen(onDetailButtonClick = navController::navigateExerciseToExerciseDetail)
        exerciseDetailScreen()
    }

}