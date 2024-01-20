package com.colddelight.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.data.util.getTodayDateWithDayOfWeek
import com.colddelight.designsystem.R
import com.colddelight.designsystem.component.DateWithCnt
import com.colddelight.designsystem.component.ExerciseProgress
import com.colddelight.designsystem.component.MainButton
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
    onDetailButtonClick: () -> Unit,
    onFinishClick: () -> Unit,

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
            ExerciseContentWithState(onDetailButtonClick, uiState = exerciseUiState, onConfirm = {
                viewModel.finExercise()
                onFinishClick()
            })
        }
    }
}

@Composable
private fun ExerciseContentWithState(
    onDetailButtonClick: () -> Unit,
    uiState: ExerciseUiState,
    onConfirm: () -> Unit,
) {
    when (uiState) {

        is ExerciseUiState.Loading -> Text(text = "dd")
        is ExerciseUiState.Error -> Text(text = uiState.msg)
        is ExerciseUiState.Success -> ExerciseContent(
            onDetailButtonClick,
            uiState.routineInfo, uiState.exerciseList,
            uiState
                .curIndex,
            onConfirm = { onConfirm() },
        )
    }
}

@Composable
private fun ExerciseContent(
    onDetailButtonClick: () -> Unit,
    routineInfo: TodayRoutine,
    exerciseList: List<Exercise>,
    cur: Int,
    onConfirm: () -> Unit,
) {

    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        FinishDialog(
            onDismiss = { showDialog = false },
            onConfirm = onConfirm,
            count = 11,
            exerciseCnt = 5,
            setCnt = 15
        )
    }
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
                    .padding(16.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f), Alignment.Center
            ) {
                if (cur == exerciseList.size) {
                    ExerciseDoneButton { showDialog = true }
                } else {
                    ExerciseButton(exerciseList[cur], onDetailButtonClick)
                }
            }
            SubButton(
                onClick = {
                    showDialog = true
                },
                Modifier.padding(bottom = 16.dp),
                content = { Text("전체 완료", style = NotoTypography.bodyMedium, color = Main) })

        }
        itemsIndexed(exerciseList) { index, item ->
            if (cur == index) {
                CurExerciseItem(item)
            } else {
                if (item.isDone) {
                    DoneExerciseItem(item.name)
                } else {
                    TodoExerciseItem(item.name)
                }
            }
        }
    }
    fun f() {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishDialog(
    count: Int,
    exerciseCnt: Int,
    setCnt: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .fillMaxHeight(0.3F)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkGray, RoundedCornerShape(10.dp)),
                Arrangement.SpaceEvenly,
                Alignment.CenterHorizontally
            ) {

                Text(
                    text = "운동결과", style = NotoTypography.headlineSmall,
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = "Count :", style = HortaTypography.bodyMedium)
                    Text(
                        text = "$count".padStart(4, ' '),
                        style = NotoTypography.headlineSmall,
                        color = Main
                    )
                    Text(text = " 회", style = NotoTypography.bodyMedium)

                }
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = "Exercise :", style = HortaTypography.bodyMedium)
                    Text(
                        text = "$exerciseCnt".padStart(4, ' '),
                        style = NotoTypography.headlineSmall,
                        color = Main
                    )
                    Text(text = " 세트", style = NotoTypography.bodyMedium)
                }
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = "Set : ", style = HortaTypography.bodyMedium)
                    Text(
                        text = "$setCnt".padStart(4, ' '),
                        style = NotoTypography.headlineSmall,
                        color = Main
                    )
                    Text(text = " 개", style = NotoTypography.bodyMedium)

                }
                MainButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    content = {
                        Text(
                            "완료",
                            style = NotoTypography.bodyMedium,
                            color = Color.White
                        )
                    })

            }

        }

    }
}

@Composable
fun ExerciseDoneButton(onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Main
        ),
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(Main, shape = CircleShape)
    ) {
        Text("운동완료", style = NotoTypography.headlineMedium, color = Color.White)
    }
}

@Composable
fun ExerciseButton(exercise: Exercise, onDetailButtonClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = BackGray
        ),
        onClick = { onDetailButtonClick() },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
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
fun DoneExerciseItem(name: String) {
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
            Exercise.Weight("벤치 프레스", 20, 40, 1, true),
            Exercise.Weight("스쿼트", 40, 100, 2, false),
            Exercise.Weight("데드 리프트", 40, 100, 2, false),
            Exercise.Weight("숄더 프레스", 40, 100, 2, false),
            Exercise.Calisthenics("턱걸이", 12, 3, 3, false)
        ), 1, {}
    )

}