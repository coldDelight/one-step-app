package com.colddelight.routine

import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo

sealed interface RoutineInfoUiState {
    data object Loading : RoutineInfoUiState

    data class Error(val msg: String) : RoutineInfoUiState

    data class Success(val routine: Routine) : RoutineInfoUiState
}

sealed interface RoutineDayInfoUiState {
    data object Loading : RoutineDayInfoUiState

    data class Error(val msg: String) : RoutineDayInfoUiState

    data class Success(val routineDayInfo: List<RoutineDayInfo>) : RoutineDayInfoUiState
}

sealed interface ExerciseListState {
    data object None: ExerciseListState
    data class NotNone(val exerciseList: List<ExerciseInfo>): ExerciseListState
}