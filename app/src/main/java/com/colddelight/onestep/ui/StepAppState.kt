package com.colddelight.onestep.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.colddelight.data.util.NetworkMonitor
import com.colddelight.exercise.navigation.navigateToExercise
import com.colddelight.history.navigation.HistoryRoute
import com.colddelight.history.navigation.navigateToHistory
import com.colddelight.home.navigation.HomeRoute
import com.colddelight.home.navigation.navigateHomeToExercise
import com.colddelight.home.navigation.navigateToHome
import com.colddelight.onestep.navigation.TopLevelDestination
import com.colddelight.onestep.navigation.TopLevelDestination.HOME
import com.colddelight.onestep.navigation.TopLevelDestination.HISTORY
import com.colddelight.onestep.navigation.TopLevelDestination.ROUTINE
import com.colddelight.routine.navigation.RoutineRoute
import com.colddelight.routine.navigation.navigateToRoutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberStepAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    shouldShowBottomBar : Boolean,
): StepAppState {
    return remember(
        navController,
        coroutineScope,
        networkMonitor,
        shouldShowBottomBar
    ) {
        StepAppState(
            navController,
            coroutineScope,
            networkMonitor,
            shouldShowBottomBar
        )
    }
}

@Stable
class StepAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
    var shouldShowBottomBar: Boolean,
    ) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HomeRoute.route -> HOME
            HistoryRoute.route -> HISTORY
            RoutineRoute.route -> ROUTINE
            else -> null
        }
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {

                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                HOME -> {navController.navigateToHome(topLevelNavOptions)
                shouldShowBottomBar = true}

                HISTORY -> navController.navigateToHistory(topLevelNavOptions)
                ROUTINE -> navController.navigateToRoutine(topLevelNavOptions)
            }
        }
    }

    fun navigateHomeToExercise(routineDayId: Int){
        navController.navigateHomeToExercise(routineDayId)
        shouldShowBottomBar = false
    }

//    fun navigateToExerciseDetail(){
//        navController.navigateToExerciseDetail
//    }
}