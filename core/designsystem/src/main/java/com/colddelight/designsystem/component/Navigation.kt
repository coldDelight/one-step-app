package com.colddelight.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Historyselected
import com.colddelight.designsystem.icons.iconpack.Historyunselected
import com.colddelight.designsystem.icons.iconpack.Homeselected
import com.colddelight.designsystem.icons.iconpack.Homeunselected
import com.colddelight.designsystem.icons.iconpack.Routineselected
import com.colddelight.designsystem.icons.iconpack.Routineunselected
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.OneStepTheme
import com.colddelight.designsystem.theme.TextGray

@Composable
fun RowScope.StepNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = StepNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = StepNavigationDefaults.navigationContentColor(),
            selectedTextColor = StepNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = StepNavigationDefaults.navigationContentColor(),
            indicatorColor = StepNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun StepNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = BackGray,
        contentColor = StepNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}

@Preview
@Composable
fun StepNavigationPreview() {
    val items = listOf("Routine","Home", "History")
    val icons = listOf(
        IconPack.Routineunselected,
        IconPack.Homeunselected,
        IconPack.Historyunselected
    )
    val selectedIcons = listOf(
        IconPack.Routineselected,
        IconPack.Homeselected,
        IconPack.Historyselected,

        )

    OneStepTheme {
        StepNavigationBar {
            items.forEachIndexed { index, item ->
                StepNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

object StepNavigationDefaults {
    @Composable
    fun navigationContentColor() = TextGray

    @Composable
    fun navigationSelectedItemColor() = Main

    @Composable
    fun navigationIndicatorColor() = BackGray
}