package com.colddelight.exercise


sealed interface ExerciseDetailUiState {
    data object Default : ExerciseDetailUiState
    data object During : ExerciseDetailUiState
    data object Resting : ExerciseDetailUiState
    data object Done : ExerciseDetailUiState
}