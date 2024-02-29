package com.colddelight.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.data.util.getTodayDateWithDayOfWeek
import com.colddelight.designsystem.component.ui.CategoryIconList
import com.colddelight.designsystem.component.ui.DateWithCnt
import com.colddelight.designsystem.component.ui.ExerciseProgress
import com.colddelight.designsystem.component.MainButton
import com.colddelight.designsystem.component.SubButton
import com.colddelight.designsystem.component.ui.TitleText
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.model.Exercise
import com.colddelight.model.HistoryExerciseUI
import com.colddelight.model.Routine

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
    routineInfo: Routine,
    exerciseList: List<HistoryExerciseUI>,
    cur: Int,
    onConfirm: () -> Unit,
) {

    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        FinishDialog(
            onDismiss = { showDialog = false },
            onConfirm = onConfirm,
            exerciseList = exerciseList
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {

        item {
            TodayRoutineInfo(getTodayDateWithDayOfWeek(), routineInfo, Modifier)
        }
        item {
            TitleText(text = "Routine", modifier = Modifier.padding(top = 8.dp))
        }
        item {
            val curCnt = if (cur + 1 >= exerciseList.size) exerciseList.size else cur + 1
            Row(Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = curCnt.toString(),
                    style = NotoTypography.bodyMedium,
                    color = Main,
                )
                Text(
                    text = " / ${exerciseList.size}",
                    style = NotoTypography.bodyMedium,
                )
            }
        }
        item {
            ExerciseProgress(Modifier.fillMaxWidth(), cur, exerciseList.size, true)
        }
        item {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f), Alignment.Center
            ) {
                if (cur == exerciseList.size) {
                    ExerciseDoneButton { showDialog = true }
                } else {
                    ExerciseButton(exerciseList[cur].exercise, onDetailButtonClick)
                }
            }
        }
        item {
            SubButton(
                modifier = Modifier.padding(bottom = 16.dp),
                onClick = {
                    showDialog = true
                },
                content = { Text("전체 완료", style = NotoTypography.bodyMedium, color = Main) }
            )
        }
        itemsIndexed(exerciseList) { index, item ->
            if (cur == index) {
                CurExerciseItem(item.exercise)
            } else {
                if (item.isDone) {
                    DoneExerciseItem(item.exercise.name)
                } else {
                    TodoExerciseItem(item.exercise.name)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    exerciseList: List<HistoryExerciseUI>
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5F),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkGray),
                Arrangement.SpaceAround,
                Alignment.CenterHorizontally
            ) {
                Text(
                    text = "운동결과", style = NotoTypography.headlineSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyColumn(
                    Modifier.fillMaxHeight(0.8f)
                ) {
                    items(exerciseList) { exercise ->
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(3.dp)
                                    .background(Color.White, CircleShape)
                            )
                            Text(
                                text = exercise.exercise.name,
                                style = NotoTypography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        LazyRow(
                            contentPadding = PaddingValues(16.dp),
                        ) {
                            itemsIndexed(exercise.exercise.setInfoList) { index, setInfo ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        Modifier
                                            .padding(horizontal = 8.dp)
                                            .background(Main, CircleShape)
                                            .padding(vertical = 6.dp, horizontal = 14.dp),
                                        Alignment.Center,
                                    ) {
                                        Row {
                                            Text(
                                                "${setInfo.kg}kg",
                                                style = HortaTypography.bodyMedium,
                                                color = Color.White
                                            )
                                        }
                                    }
                                    Text(
                                        text = "x  ${setInfo.reps} reps",
                                        style = HortaTypography.labelLarge
                                    )
                                    if (index < exercise.exercise.setInfoList.size - 1)
                                        Divider(
                                            modifier = Modifier
                                                .padding(horizontal = 18.dp)
                                                .height(30.dp)
                                                .width(1.dp), color = LightGray
                                        )
                                }
                            }
                        }
                    }
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
                    }
                )
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
                        style = NotoTypography.bodyLarge,
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
                style = NotoTypography.bodyLarge,
                modifier = Modifier
                    .padding(start = 50.dp)
            )

        }
        Divider(color = DarkGray, modifier = Modifier.padding(top = 16.dp), thickness = 2.dp)
    }
}

@Composable
private fun TodayRoutineInfo(date: String, routineInfo: Routine, modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        DateWithCnt(date, routineInfo.cnt)
        Text(text = routineInfo.name, style = NotoTypography.headlineLarge)
        CategoryIconList(routineInfo.categoryIdList)
    }
}


//@Preview
//@Composable
//private fun ExerciseContentPreview() {
//    val routineInfo =
//        TodayRoutine("3분할", cnt = 5, listOf(ExerciseCategory.CHEST, ExerciseCategory.BACK))
//    ExerciseContent(
//        {},
//        routineInfo,
//        listOf(
//            Exercise.Weight("벤치 프레스", 20, 40, 1, ),
//            Exercise.Weight("스쿼트", 40, 100, 2, ),
//            Exercise.Weight("데드 리프트", 40, 100, 2, ),
//            Exercise.Weight("숄더 프레스", 40, 100, 2, ),
//            Exercise.Calisthenics("턱걸이", 12, 3, 3, ),
//        ), 0, {}
//    )
//}