package com.colddelight.onestep.ui

import android.app.Activity
import android.util.LayoutDirection
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.colddelight.designsystem.component.StepNavigationBar
import com.colddelight.designsystem.component.StepNavigationBarItem
import com.colddelight.designsystem.component.StepTopAppBar
import com.colddelight.designsystem.component.TopAppBarNavigationType
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.exercise.navigation.ExerciseRoute
import com.colddelight.exercisedetail.navigation.ExerciseDetailRoute
import com.colddelight.history.navigation.HistoryRoute
import com.colddelight.home.navigation.HomeRoute
import com.colddelight.onestep.R
import com.colddelight.onestep.navigation.StepNavHost
import com.colddelight.onestep.navigation.TopLevelDestination
import com.colddelight.onestep.navigation.TopLevelDestination.HOME
import com.colddelight.onestep.navigation.TopLevelDestination.HISTORY
import com.colddelight.onestep.navigation.TopLevelDestination.ROUTINE
import com.colddelight.routine.navigation.RoutineRoute
import kotlinx.coroutines.launch

@Composable
fun StepApp(
    appState: StepAppState = rememberStepAppState(
        shouldShowBottomBar = true
    ),
    onAppLogoClick: () -> Unit
) {
    val currentDestination: String = appState.currentDestination?.route ?: HomeRoute.route

    val destination = appState.currentTopLevelDestination
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    BackOnPressed()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(){
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                // ...other drawer items
            }

        }
    ) {
        Scaffold(
            containerColor = BackGray,
            topBar = {
                StepTopBar(
                    currentDestination = currentDestination,
                    modifier = Modifier.testTag("StepTopBar"),
                    onAppLogoClick = { onAppLogoClick() },
                    onNavigationClick = appState::popBackStack,
                    onDrawerClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
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
                    StepNavHost(appState = appState)
                }
            }
        }

    }

}

@Composable
fun BackOnPressed() {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 1000L) {
            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}

@Composable
private fun StepTopBar(
    currentDestination: String,
    modifier: Modifier = Modifier,
    onAppLogoClick: () -> Unit,
    onNavigationClick: () -> Unit,
    onDrawerClick: () -> Unit,
) {
    StepTopAppBar(
        titleRes = when (currentDestination) {
            HomeRoute.route -> R.string.blank
            HistoryRoute.route -> R.string.history
            RoutineRoute.route -> R.string.routine
            ExerciseRoute.route -> R.string.exercise
            ExerciseDetailRoute.route -> R.string.exercise_detail
            else -> R.string.exercise
        },
        navigationType = when (currentDestination) {
            HomeRoute.route -> TopAppBarNavigationType.Home
            HistoryRoute.route, RoutineRoute.route -> TopAppBarNavigationType.Empty
            else -> TopAppBarNavigationType.Back
        },
        onAppLogoClick = { onAppLogoClick() },
        onNavigationClick = onNavigationClick,
        onDrawerClick = onDrawerClick
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

