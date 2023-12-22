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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.data.util.getTodayDateWithDayOfWeek
import com.colddelight.designsystem.R
import com.colddelight.designsystem.component.DateWithCnt
import com.colddelight.designsystem.component.ExerciseProgress
import com.colddelight.designsystem.component.SubButton
import com.colddelight.designsystem.component.TitleText
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.model.Exercise
import com.colddelight.model.TodayRoutine
import com.colddelight.model.ExerciseCategory

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = hiltViewModel(),
    onDetailButtonClick: (Int) -> Unit
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
            ExerciseContentWithState(onDetailButtonClick, uiState = exerciseUiState)
        }
    }
}

@Composable
private fun ExerciseContentWithState(onDetailButtonClick: (Int) -> Unit, uiState: ExerciseUiState) {
    when (uiState) {
        is ExerciseUiState.Loading -> ExerciseLoading()
        is ExerciseUiState.Error -> Text(text = uiState.msg)
        is ExerciseUiState.Success -> ExerciseContent(
            onDetailButtonClick,
            uiState.routineInfo, uiState.exerciseList, uiState
                .cur
        )
    }
}

@Composable
private fun ExerciseContent(
    onDetailButtonClick: (Int) -> Unit,
    routineInfo: TodayRoutine,
    exerciseList: List<Exercise>,
    cur: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            TodayRoutineInfo(getTodayDateWithDayOfWeek(), routineInfo, Modifier)
            TitleText(text = "Routine", modifier = Modifier.padding(top = 8.dp))

            ExerciseProgress(Modifier.fillMaxWidth(), cur, exerciseList.size)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), Alignment.Center
            ) {
                ExerciseButton(exerciseList[cur], onDetailButtonClick)
            }
            SubButton(
                {},
                content = { Text("전체 완료", style = NotoTypography.bodyMedium, color = Main) })

        }
        itemsIndexed(exerciseList) { index, item ->
            if (cur == index) {
                CurExerciseItem(item)
            } else {
                if (item.isDone) {
                    DoneExerciseItem(item.name, item.time)
                } else {
                    TodoExerciseItem(item.name)
                }
            }
        }


    }
}

@Composable
fun ExerciseButton(exercise: Exercise, onDetailButtonClick: (Int) -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = BackGray
        ),
        onClick = { onDetailButtonClick(11) },
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
fun CurExerciseItem(item: Exercise) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Main, shape = CircleShape)
    ) {
        Text(
            item.name,
            style = NotoTypography.bodyLarge,
            color = Color.White,
            modifier = Modifier
                .padding(start = 50.dp)
                .padding(top = 16.dp, bottom = 16.dp)
        )
    }
}

@Composable
fun DoneExerciseItem(name: String, time: String) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = name,
                        style = HortaTypography.bodyLarge,
                        modifier = Modifier
                            .padding(start = 50.dp)
                    )
                    // Line on the left
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .fillMaxWidth()
                            .background(Main)
                    )
                }
            }
        }
        Divider(color = DarkGray, modifier = Modifier.padding(top = 16.dp), thickness = 2.dp)
    }
}

@Composable
fun TodoExerciseItem(name: String) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = HortaTypography.bodyLarge,
                modifier = Modifier
                    .padding(start = 50.dp)
            )

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
        modifier = modifier
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
        {},
        routineInfo,
        listOf(
            Exercise.Weight("벤치 프레스", 20, 40, "+ 15 min 47sec", 1, true),
            Exercise.Weight("스쿼트", 40, 100, "", 2, false),
            Exercise.Weight("데드 리프트", 40, 100, "", 2, false),
            Exercise.Weight("숄더 프레스", 40, 100, "", 2, false),
            Exercise.Calisthenics("턱걸이", 12, 3, "", 3, false)
        ), 1
    )

}