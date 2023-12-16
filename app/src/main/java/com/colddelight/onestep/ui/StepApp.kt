package com.colddelight.onestep.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.colddelight.data.util.LoginHelper
import com.colddelight.data.util.NetworkMonitor
import com.colddelight.designsystem.component.StepNavigationBar
import com.colddelight.designsystem.component.StepNavigationBarItem
import com.colddelight.designsystem.component.StepTopAppBar
import com.colddelight.designsystem.component.TopAppBarNavigationType
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.TextGray
import com.colddelight.exercise.navigation.ExerciseRoute
import com.colddelight.history.navigation.HistoryRoute
import com.colddelight.home.navigation.HomeRoute
import com.colddelight.onestep.R
import com.colddelight.onestep.navigation.StepNavHost
import com.colddelight.onestep.navigation.TopLevelDestination
import com.colddelight.onestep.navigation.TopLevelDestination.HOME
import com.colddelight.onestep.navigation.TopLevelDestination.HISTORY
import com.colddelight.onestep.navigation.TopLevelDestination.ROUTINE
import com.colddelight.routine.navigation.RoutineRoute

@Composable
fun StepApp(
    networkMonitor: NetworkMonitor,
    loginHelper: LoginHelper,
    appState: StepAppState = rememberStepAppState(
        networkMonitor = networkMonitor,
        loginHelper = loginHelper,
        shouldShowBottomBar = true
    ),
) {
    val currentDestination: String = appState.currentDestination?.route ?: HomeRoute.route

    val destination = appState.currentTopLevelDestination
    Scaffold(
        containerColor = BackGray,

        topBar = {
            StepTopBar(
                currentDestination = currentDestination,
                modifier = Modifier.testTag("StepTopBar"),
                onNavigationClick = appState::popBackStack
            )
        },
        bottomBar = {
            when (destination) {
                HOME, HISTORY, ROUTINE ->
                    StepBottomBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination,
                        modifier = Modifier.testTag("StepBottomBar"),
                    )

                else -> {}
            }
        }
    ) { padding ->

        val isOffline by appState.isOffline.collectAsStateWithLifecycle()
        val isLogin by appState.isLogin.collectAsStateWithLifecycle()
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            Column(Modifier.fillMaxSize()) {
//                Text(text = "로그인 상태 $isLogin",color= TextGray)

                // Show the top app bar on top level destinations.
                if (destination != null) {
                    Text(text = "$isOffline")
                }

                StepNavHost(appState = appState)
            }
        }
    }
}

@Composable
private fun StepTopBar(
    currentDestination: String,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
) {
    StepTopAppBar(
        titleRes = when (currentDestination) {
            HomeRoute.route -> R.string.blank
            HistoryRoute.route -> R.string.history
            RoutineRoute.route -> R.string.routine
            ExerciseRoute.detailRoute(1) -> R.string.exercise
            else -> R.string.exercise
        },
        navigationType = when (currentDestination) {
            HomeRoute.route -> TopAppBarNavigationType.Home
            HistoryRoute.route, RoutineRoute.route -> TopAppBarNavigationType.Empty
            else -> TopAppBarNavigationType.Back
        },
        onNavigationClick = onNavigationClick
    )
}

@Composable
private fun StepBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    StepNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            StepNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

