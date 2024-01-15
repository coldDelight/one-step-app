package com.colddelight.exercise

import com.colddelight.model.Exercise
import com.colddelight.model.TodayRoutine

sealed interface ExerciseUiState {
    data object Loading : ExerciseUiState

    data class Error(val msg: String) : ExerciseUiState

    data class Success(
        val routineInfo: TodayRoutine,
        val curIndex: Int,
        val exerciseList: List<Exercise>,
    ) : ExerciseUiState

}