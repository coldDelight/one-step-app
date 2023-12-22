package com.colddelight.exercisedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.exercise.ExerciseUiState
import com.colddelight.exercise.ExerciseViewModel
import com.colddelight.model.Exercise

@Composable
fun ExerciseDetailScreen(
    exerciseDetailViewModel: ExerciseViewModel = hiltViewModel(),
) {
    val exerciseUiState by exerciseDetailViewModel.exerciseUiState.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (exerciseUiState) {
                is ExerciseUiState.Success -> {
                    ExerciseContentWithState2(exerciseUiState)
                    Text(
                        text = "엑설 디테일 성공임 ",
                    )
                }

                is ExerciseUiState.Loading -> {
                    Text(
                        text = "엑설 디테일 로딩임   ",
                    )
                }

                is ExerciseUiState.Error -> {
                    Text(
                        text = "엑설 디테일 실패임 ",
                    )
                }


            }

        }
    }
}

@Composable
private fun ExerciseContentWithState2(
    uiState: ExerciseUiState
) {
    when (uiState) {
        is ExerciseUiState.Loading -> Text(text = "로딩ㅇ이요")
        is ExerciseUiState.Error -> Text(text = uiState.msg)
        is ExerciseUiState.Success -> ExerciseYmp(
            uiState.exerciseList
        )
    }
}


@Composable
private fun ExerciseYmp(testList: List<Exercise>) {

    Text(text = testList[0].name)
}