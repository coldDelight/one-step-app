package com.colddelight.routine

import com.colddelight.model.Routine

sealed interface RoutineUiState {
    data object Loading : RoutineUiState

    data class Error(val msg: String) : RoutineUiState

    data class Success(val routine: Routine) : RoutineUiState
}