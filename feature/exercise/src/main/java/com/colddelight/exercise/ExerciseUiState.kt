package com.colddelight.exercise

import com.colddelight.model.HistoryExerciseUI
import com.colddelight.model.Routine

sealed interface ExerciseUiState {
    data object Loading : ExerciseUiState

    data class Error(val msg: String) : ExerciseUiState

    data class Success(
        val routineInfo: Routine,
        val curIndex: Int,
        val exerciseList: List<HistoryExerciseUI>,
    ) : ExerciseUiState

}