package com.colddelight.exercise


sealed interface ExerciseDetailUiState {
    val curSet: Int
    data class Default(override val curSet: Int) : ExerciseDetailUiState
    data class During(override val curSet: Int) : ExerciseDetailUiState
    data class Resting(override val curSet: Int) : ExerciseDetailUiState
    data class Done(override val curSet: Int) : ExerciseDetailUiState


}