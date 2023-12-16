package com.colddelight.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.data.util.getTodayDateWithDayOfWeek
import com.colddelight.designsystem.R
import com.colddelight.designsystem.component.DateWithCnt
import com.colddelight.designsystem.component.TitleText
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.model.RoutineInfo
import com.colddelight.model.ExerciseCategory

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = hiltViewModel(),
) {
    val sessionUiState by viewModel.exerciseUiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackGray
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ExerciseContentWithState(uiState = sessionUiState)
        }
    }
}

@Composable
private fun ExerciseContentWithState(uiState: ExerciseUiState) {
    when (uiState) {
        is ExerciseUiState.Loading -> ExerciseLoading()
        is ExerciseUiState.Error -> ExerciseLoading()
        is ExerciseUiState.Success -> ExerciseContent(uiState.routineInfo)
    }
}

@Composable
private fun ExerciseContent(routineInfo: RoutineInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        TodayRoutineInfo(getTodayDateWithDayOfWeek(), routineInfo)
        TitleText(text = "Routine", modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
private fun ExerciseLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun TodayRoutineInfo(date: String, routineInfo: RoutineInfo) {
    Column(
    ) {
        DateWithCnt(date, routineInfo.cnt)
        Text(text = routineInfo.name, style = NotoTypography.headlineLarge)
        CategoryIconList(routineInfo.categoryIdList)
    }
}


@Composable
fun CategoryIconList(categoryList: List<ExerciseCategory>) {
    LazyRow {
        items(categoryList) { item ->
            Box(modifier = Modifier.padding(4.dp)) {
                when (item) {
                    ExerciseCategory.CHEST -> Image(
                        painter = painterResource(id = R.drawable.chest),
                        contentDescription = "가슴",
                    )

                    ExerciseCategory.SHOULDER -> Image(
                        painter = painterResource(id = R.drawable.shoulder),
                        contentDescription = "어깨",
                    )

                    ExerciseCategory.BACK -> Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "등",
                    )

                    ExerciseCategory.ARM -> Image(
                        painter = painterResource(id = R.drawable.arm),
                        contentDescription = "팔",
                    )

                    ExerciseCategory.LEG -> Image(
                        painter = painterResource(id = R.drawable.leg),
                        contentDescription = "하체",
                    )

                    ExerciseCategory.CARDIO -> Image(
                        painter = painterResource(id = R.drawable.cardio),
                        contentDescription = "유산소",
                    )

                    ExerciseCategory.CALISTHENICS -> Image(
                        painter = painterResource(id = R.drawable.calisthenics),
                        contentDescription = "맨몸",
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun ExerciseContentPreview() {
    val routineInfo =
        RoutineInfo("3분할", cnt = 5, listOf(ExerciseCategory.CHEST, ExerciseCategory.BACK))
    ExerciseContent(routineInfo)

}

@Preview
@Composable
private fun RoutineInfoPreview() {
    val routineInfo =
        RoutineInfo("3분할", cnt = 5, listOf(ExerciseCategory.CHEST, ExerciseCategory.BACK))
    TodayRoutineInfo(date = "2023.11.15 토", routineInfo = routineInfo)

}
