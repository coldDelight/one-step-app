package com.colddelight.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.data.util.getDayOfWeekEn
import com.colddelight.data.util.getTodayDate
import com.colddelight.designsystem.component.CategoryChip
import com.colddelight.designsystem.component.DateWithCnt
import com.colddelight.designsystem.component.MainButton
import com.colddelight.designsystem.component.TitleText
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Day
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.designsystem.theme.TextGray
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.ExerciseDay

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onStartButtonClick: () -> Unit
) {
    val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackGray
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            HomeContentWithState(
                uiState = homeUiState
            ) {
                onStartButtonClick()
            }
        }
    }
}

@Composable
private fun HomeContentWithState(
    uiState: HomeUiState,
    onStartButtonClick: () -> Unit
) {
    when (uiState) {
        is HomeUiState.Loading -> {}
        is HomeUiState.Error -> Text(text = uiState.msg)
        is HomeUiState.Success ->
            HomeContent(
                uiState.cnt,
                uiState.state,
                uiState.exerciseWeek,
                uiState.today,
                onStartButtonClick
            )
    }
}

@Composable
private fun HomeContent(
    cnt: Int,
    state: HomeState,
    exerciseWeek: List<ExerciseDay>,
    today: Int,
    onStartButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column {
            TitleText(text = "Week", modifier = Modifier.padding(top = 8.dp))
            ExerciseWeek(exerciseWeek, today)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleText(text = "Today", modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
                DateWithCnt(getTodayDate(), cnt)
            }

            HomeButton(state, onStartButtonClick)

            when (state) {
                is HomeState.During -> {
                    ToExerciseButton(
                        onStartButtonClick,
                        Modifier
                            .fillMaxWidth()
                    )
                }

                else -> {
                    Spacer(Modifier)
                }
            }
        }

    }
}

@Composable
fun ExerciseWeek(exerciseWeek: List<ExerciseDay>, today: Int) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        items(exerciseWeek) {
            WeekItem(it, today)
        }
    }
}

@Composable
fun WeekItem(exerciseDay: ExerciseDay, today: Int) {
    val isToday = today == exerciseDay.dayOfWeekId
    val itemColor = when {
        exerciseDay.isExerciseDone -> Main
        exerciseDay.isRestDay -> DarkGray
        else -> TextGray
    }
    val modifier = if (isToday) {
        Modifier
            .border(width = 2.dp, color = Main, shape = CircleShape)
    } else {
        Modifier
    }

    Column(
        modifier = modifier.padding(vertical = 14.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = IconPack.Day, contentDescription = "d", tint = itemColor)
        Spacer(modifier = Modifier.height(8.dp))
        Text(exerciseDay.dayOfWeek, style = HortaTypography.bodyMedium, color = itemColor)
    }


}

@Composable
fun HomeButton(state: HomeState, onStartButtonClick: () -> Unit) {
    val borderWidthRatio = 0.2f
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = BackGray,
            disabledContainerColor = BackGray
        ),
        enabled = state is HomeState.During,
        onClick = { onStartButtonClick() },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(0.8f)
            .aspectRatio(1f)
            .background(BackGray, shape = CircleShape)
            .border(
                width = with(LocalDensity.current) { (LocalDensity.current.density * 8.dp.toPx() * borderWidthRatio).toDp() },
                color = state.strokeColor,
                shape = CircleShape
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = state.text, style = NotoTypography.headlineMedium, color = state.textColor)
            if (state is HomeState.During) {
                LazyRow() {
                    items(state.categoryList) {
                        CategoryChip(
                            isCanDelete = false,
                            label = ExerciseCategory.toName(it.id),
                            categoryId = it.id,
                            size = 16,
                            onDeleteClicked = { }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ToExerciseButton(onClick: () -> Unit, modifier: Modifier) {
    MainButton(
        onClick = onClick,
        content = {
            Text(
                text = "운동시작",
                style = NotoTypography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        },
        modifier = modifier
    )
}

@Preview(name = "SMALL", device = Devices.PIXEL)
@Composable
fun homePreview() {
    HomeContent(
        11,
//        HomeState.Resting(),
        HomeState.During(
            text = "3분할",
            categoryList = listOf(ExerciseCategory.CHEST, ExerciseCategory.BACK)
        ),
        listOf(
            ExerciseDay(1, getDayOfWeekEn(1), false, false),
            ExerciseDay(2, getDayOfWeekEn(2), true, true),
            ExerciseDay(3, getDayOfWeekEn(3), false, false),
            ExerciseDay(4, getDayOfWeekEn(4), true, true),
            ExerciseDay(5, getDayOfWeekEn(5), false, false),
            ExerciseDay(6, getDayOfWeekEn(6), true, false),
            ExerciseDay(7, getDayOfWeekEn(7), true, false)
        ), 6, {})
}

