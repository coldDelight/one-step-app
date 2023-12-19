package com.colddelight.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.data.util.getTodayDateWithDayOfWeek
import com.colddelight.designsystem.R
import com.colddelight.designsystem.component.DateWithCnt
import com.colddelight.designsystem.component.StepButton
import com.colddelight.designsystem.component.TitleText
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.model.Exercise
import com.colddelight.model.TodayRoutine
import com.colddelight.model.ExerciseCategory
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = hiltViewModel(),
) {
    val exerciseUiState by viewModel.exerciseUiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackGray
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ExerciseContentWithState(uiState = exerciseUiState)
        }
    }
}

@Composable
private fun ExerciseContentWithState(uiState: ExerciseUiState) {
    when (uiState) {
        is ExerciseUiState.Loading -> ExerciseLoading()
        is ExerciseUiState.Error -> Text(text = uiState.msg)
        is ExerciseUiState.Success -> ExerciseContent(uiState.routineInfo, uiState.exerciseList)
    }
}

@Composable
private fun ExerciseContent(routineInfo: TodayRoutine, exerciseList: List<Exercise>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        TodayRoutineInfo(getTodayDateWithDayOfWeek(), routineInfo, Modifier)
        TitleText(text = "Routine", modifier = Modifier.padding(top = 8.dp))
        ExerciseProgress(0, exerciseList.size)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                , Alignment.Center
        ) {
            ExerciseButton(exerciseList[0])
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            StepButton(
                {},
                color = DarkGray,
                content = { Text("전체 완료", style = NotoTypography.bodyMedium, color = Main) })
            Row(modifier = Modifier.padding(top = 10.dp)) {
                CircleDot(Main)
                Text(
                    "0 H 48 min",
                    style = HortaTypography.bodyMedium,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }

        ExerciseList(exerciseList, Modifier)
    }
}


@Composable
fun ExerciseButton(exercise: Exercise) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = BackGray
        ),
        onClick = { },
        modifier = Modifier
            .size(300.dp)
            .background(Main, shape = CircleShape)
            .border(
                width = 4.dp,
                color = Main,
                shape = CircleShape
            )
    ) {
        Box(
            modifier = Modifier
        ) {
            when (exercise) {
                is Exercise.Weight -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier.weight(3F),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(exercise.name, style = NotoTypography.headlineMedium)
                            Text("min : ${exercise.min}kg", style = NotoTypography.bodyMedium)
                            Text("max : ${exercise.max}kg", style = NotoTypography.bodyMedium)

                        }
                        Text(
                            "운동하기",
                            style = NotoTypography.bodyMedium,
                            color = Main,
                            modifier = Modifier.weight(0.5F)
                        )

                    }
                }

                is Exercise.Calisthenics -> {
                    Text(exercise.name, style = NotoTypography.headlineMedium)
                }
            }
        }
    }
}

@Composable
fun ExerciseProgress(current: Int, exerciseCnt: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterHorizontally
        )
    ) {
        repeat(exerciseCnt) { index ->
            val circleColor = if (index <= current) Main else LightGray
            CircleDot(color = circleColor)
        }
    }
}

@Composable
fun CircleDot(color: Color) {
    Box(
        modifier = Modifier
            .size(14.dp)
            .background(color, shape = CircleShape)
    )
}


@Composable
fun ExerciseList(exerciseList: List<Exercise>, modifier: Modifier) {
    LazyColumn(modifier = modifier.padding(bottom = 16.dp)) {
        items(exerciseList) { item ->
            ExerciseListItem(item)
        }
    }
    Text(text = "+ 추가운동이여", style = NotoTypography.bodyLarge, color = LightGray)
}

@Composable
fun ExerciseListItem(item: Exercise) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        when (item) {
            is Exercise.Weight -> {
                Text(item.name, style = NotoTypography.bodyLarge)
            }

            is Exercise.Calisthenics -> {
                Text(item.name, style = NotoTypography.bodyLarge)
            }
        }
        Divider(color = DarkGray, modifier = Modifier.padding(top = 16.dp), thickness = 2.dp)
    }
}


@Composable
private fun ExerciseLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun TodayRoutineInfo(date: String, routineInfo: TodayRoutine, modifier: Modifier) {
    Column(
        modifier = Modifier
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
        TodayRoutine("3분할", cnt = 5, listOf(ExerciseCategory.CHEST, ExerciseCategory.BACK))
    ExerciseContent(
        routineInfo,
        listOf(
            Exercise.Weight("벤치 프레스", 20, 40, "", 1),
            Exercise.Weight("스쿼트", 40, 100, "", 2),
            Exercise.Weight("데드 리프트", 40, 100, "", 2),
            Exercise.Weight("숄더 프레스", 40, 100, "", 2),
            Exercise.Calisthenics("턱걸이", 12, 3, "", 3)
        )
    )

}


