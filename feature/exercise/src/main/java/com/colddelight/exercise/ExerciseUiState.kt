package com.colddelight.exercise

import com.colddelight.model.RoutineInfo

sealed interface ExerciseUiState {
    data object Loading : ExerciseUiState

    data class Error(val msg: String) : ExerciseUiState

    data class Success(val routineInfo: RoutineInfo) : ExerciseUiState

}