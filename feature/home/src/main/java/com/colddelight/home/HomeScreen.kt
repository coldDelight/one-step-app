package com.colddelight.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.data.util.getDayOfWeekEn
import com.colddelight.data.util.getTodayDate
import com.colddelight.designsystem.component.DateWithCnt
import com.colddelight.designsystem.component.MainButton
import com.colddelight.designsystem.component.SubButton
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
            HomeContentWithState(uiState = homeUiState, onStartButtonClick)
        }
    }
}


@Composable
private fun HomeContentWithState(uiState: HomeUiState, onStartButtonClick: () -> Unit) {
    when (uiState) {
        is HomeUiState.Loading -> {}
        is HomeUiState.Error -> Text(text = uiState.msg)
        is HomeUiState.Success -> HomeContent(
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
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            TitleText(text = "Week", modifier = Modifier.padding(top = 8.dp))
            ExerciseWeek(exerciseWeek, today)
        }
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleText(text = "Today", modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
                DateWithCnt(getTodayDate(), cnt)
            }
            HomeButton(state, onStartButtonClick)
        }
        when (state) {
            is HomeState.During -> {
                Row(modifier = Modifier.fillMaxWidth()) {
                    ToFreeButton(
                        onStartButtonClick,
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                    ToExerciseButton(
                        onStartButtonClick,
                        Modifier
                            .fillMaxWidth()
                            .weight(2f)
                            .padding(start = 16.dp)
                    )
                }
            }

            else -> {
                ToFreeButton(onStartButtonClick, Modifier.fillMaxWidth())
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

//            Divider(
//                modifier = Modifier
//                    .height(40.dp)
//                    .width(1.dp).background(DarkGray)
//            )
        }


    }
}

@Composable
fun WeekItem(exerciseDay: ExerciseDay, today: Int) {
    val itemColor = when {
        today == exerciseDay.dayOfWeekId -> Main
        exerciseDay.isRestDay -> DarkGray
        else -> TextGray
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
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
            containerColor = BackGray
        ),
        onClick = { onStartButtonClick() },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(Main, shape = CircleShape)
            .border(
                width = with(LocalDensity.current) { (LocalDensity.current.density * 8.dp.toPx() * borderWidthRatio).toDp() },
                color = state.strokeColor,
                shape = CircleShape
            )
    ) {

        Column {
            Text(text = state.text, style = NotoTypography.headlineMedium, color = state.textColor)

            when (state) {
                is HomeState.During -> {
                    //카테고리 리스트 추가 리스트는 CHAN1
                    //state.categoryList : List<ExerciseCategory>
                    Text(text = state.categoryList.toString())
                }

                else -> {}
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

@Composable
fun ToFreeButton(onClick: () -> Unit, modifier: Modifier) {
    SubButton(
        onClick = onClick,
        content = {
            Text(
                text = "자유운동",
                style = NotoTypography.bodyMedium,
                color = Main,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }, modifier = modifier
    )
}


@Preview
@Composable
fun homePreview() {
    HomeContent(
        11,
//        HomeState.Done(),
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

