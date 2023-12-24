package com.colddelight.exercisedetail

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.designsystem.component.ExerciseProgress
import com.colddelight.designsystem.component.MainButton
import com.colddelight.designsystem.component.TitleText
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.exercise.CategoryIconList
import com.colddelight.exercise.ExerciseUiState
import com.colddelight.exercise.ExerciseViewModel
import com.colddelight.model.Exercise
import com.colddelight.model.SetInfo

@Composable
fun ExerciseDetailScreen(
    exerciseDetailViewModel: ExerciseViewModel = hiltViewModel(),
) {
    val exerciseDetailUiState by exerciseDetailViewModel.exerciseUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ExerciseDetailContentWithState(uiState = exerciseDetailUiState)
        }
    }
}

@Composable
private fun ExerciseDetailContentWithState(uiState: ExerciseUiState) {
    when (uiState) {
        is ExerciseUiState.Loading -> {}
        is ExerciseUiState.Error -> Text(text = uiState.msg)
        is ExerciseUiState.Success -> ExerciseDetailContent(
            uiState.exerciseList[uiState.curIndex], 0
        )
    }
}

@Composable
private fun ExerciseDetailContent(
    exercise: Exercise,
    cur: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            ExerciseInfo(exercise, Modifier)
            TitleText(text = "Set", modifier = Modifier.padding(top = 8.dp))

            ExerciseProgress(Modifier.fillMaxWidth(), cur, exercise.setInfoList.size)

            CurrentSetButtons(exercise.setInfoList[cur])

            MainButton({}, content = {
                Text(
                    "세트 시작",
                    style = NotoTypography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(2.dp)
                )
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            )

        }

        itemsIndexed(exercise.setInfoList) { index, item ->
            Text(text = "kg = ${item.kg} set = ${item.reps}", style = NotoTypography.bodyMedium)

        }


    }
}

@Composable
private fun ExerciseInfo(exercise: Exercise, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = exercise.name, style = NotoTypography.headlineMedium)
        CategoryIconList(listOf(exercise.category))
    }
}

@Composable
private fun CurrentSetButtons(setInfo: SetInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(BackGray, shape = CircleShape)
                .border(
                    width = 3.dp,
                    color = Main,
                    shape = CircleShape
                ),
            Alignment.Center
        ) {
            Text("${setInfo.kg}kg", style = NotoTypography.bodyMedium)
        }
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(BackGray, shape = CircleShape)
                .border(
                    width = 3.dp,
                    color = Main,
                    shape = CircleShape
                ),
            Alignment.Center

        ) {
            Text("${setInfo.reps}회", style = NotoTypography.bodyMedium)

        }

    }
}

@Preview
@Composable
private fun ExerciseDetailContentPreview() {
    ExerciseDetailContent(
        Exercise.Weight(
            "벤치 프레스",
            20,
            40,
            1,
            true,
            setInfoList = listOf(SetInfo(20, 12), SetInfo(40, 12), SetInfo(60, 12))
        ),
        0
    )

}

