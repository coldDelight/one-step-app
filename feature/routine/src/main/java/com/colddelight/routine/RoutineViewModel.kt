package com.colddelight.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.RoutineRepository2
import com.colddelight.domain.usecase.routine.GetRoutineUseCase
import com.colddelight.domain.usecase.routine.UpsertRoutineUseCase
import com.colddelight.model.DayExercise
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    getRoutineUseCase: GetRoutineUseCase,
    private val repository: RoutineRepository2,
    private val upsertRoutineUseCase: UpsertRoutineUseCase,
) : ViewModel() {


    val exerciseListState: StateFlow<ExerciseListState> =
        repository.getAllExerciseList()
            .map { ExerciseListState.NotNone(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ExerciseListState.None
            )

    fun upsertRoutine(routine: Routine) {
        viewModelScope.launch {
            upsertRoutineUseCase(routine)
        }
    }

    fun insertRoutineDay(routineDayInfo: RoutineDayInfo) {
        viewModelScope.launch {
            repository.insertRoutineDay(routineDayInfo)
        }
    }

    fun insertExercise(exerciseInfo: ExerciseInfo) {
        viewModelScope.launch {
            repository.insertExercise(exerciseInfo)
        }
    }

    fun insertDayExercise(dayExercise: DayExercise) {
        viewModelScope.launch {
            repository.insertDayExercise(dayExercise)
        }
    }

    fun deleteRoutineDay(routineDayId: Int) {
        viewModelScope.launch {
            repository.deleteRoutineDay(routineDayId)
        }
    }

    fun deleteExercise(exerciseId: Int) {
        viewModelScope.launch {
            repository.deleteExercise(exerciseId)
        }
    }

    fun deleteDayExercise(dayExerciseId: Int) {
        viewModelScope.launch {
            repository.deleteDayExercise(dayExerciseId)
        }
    }


    val routineInfoUiState: StateFlow<RoutineInfoUiState> =
        getRoutineUseCase()
            .map { RoutineInfoUiState.Success(it) }
            .catch { throwable -> RoutineInfoUiState.Error(throwable.message ?: "Error") }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RoutineInfoUiState.Loading
            )

    val routineDayInfoUiState: StateFlow<RoutineDayInfoUiState> =
        repository.getRoutineWeekInfo()
            .map { RoutineDayInfoUiState.Success(it) }
            .catch { throwable -> RoutineDayInfoUiState.Error(throwable.message ?: "Error") }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RoutineDayInfoUiState.Loading
            )

}
