package com.colddelight.onestep.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Historyselected
import com.colddelight.designsystem.icons.iconpack.Historyunselected
import com.colddelight.designsystem.icons.iconpack.Homeselected
import com.colddelight.designsystem.icons.iconpack.Homeunselected
import com.colddelight.designsystem.icons.iconpack.Routineselected
import com.colddelight.designsystem.icons.iconpack.Routineunselected
import com.colddelight.history.R as HistoryR
import com.colddelight.home.R as HomeR
import com.colddelight.routine.R as RoutineR

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    ROUTINE(
        selectedIcon = IconPack.Routineselected,
        unselectedIcon = IconPack.Routineunselected,
        iconTextId = RoutineR.string.routine,
        titleTextId = RoutineR.string.routine,
    ),
    HOME(
        selectedIcon = IconPack.Homeselected,
        unselectedIcon = IconPack.Homeunselected,
        iconTextId = HomeR.string.home,
        titleTextId = HomeR.string.home,
    ),
    HISTORY(
        selectedIcon = IconPack.Historyselected,
        unselectedIcon = IconPack.Historyunselected,
        iconTextId = HistoryR.string.history,
        titleTextId = HistoryR.string.history,
    )
}