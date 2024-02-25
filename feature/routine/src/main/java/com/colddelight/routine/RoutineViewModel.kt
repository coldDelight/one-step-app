package com.colddelight.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.domain.usecase.dayExercise.DeleteDayExerciseUseCase
import com.colddelight.domain.usecase.dayExercise.InsertDayExerciseUseCase
import com.colddelight.domain.usecase.exercise.DeleteExerciseUseCase
import com.colddelight.domain.usecase.exercise.GetAllExerciseListUseCase
import com.colddelight.domain.usecase.exercise.UpsertExerciseUseCase
import com.colddelight.domain.usecase.routine.GetRoutineUseCase
import com.colddelight.domain.usecase.routine.UpsertRoutineUseCase
import com.colddelight.domain.usecase.routineday.DeleteRoutineDayUseCase
import com.colddelight.domain.usecase.routineday.GetRoutineDayListUseCase
import com.colddelight.domain.usecase.routineday.UpsertRoutineDayUseCase
import com.colddelight.model.DayExerciseWithExercise
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDay
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
    private val upsertRoutineUseCase: UpsertRoutineUseCase,
    getRoutineDayListUseCase: GetRoutineDayListUseCase,
    private val upsertRoutineDayUseCase: UpsertRoutineDayUseCase,
    private val deleteRoutineDayUseCase: DeleteRoutineDayUseCase,
    private val deleteDayExerciseUseCase: DeleteDayExerciseUseCase,
    private val insertDayExerciseUseCase: InsertDayExerciseUseCase,
    getAllExerciseListUseCase: GetAllExerciseListUseCase,
    private val upsertExerciseUseCase: UpsertExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
) : ViewModel() {

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
        getRoutineDayListUseCase()
            .map { RoutineDayInfoUiState.Success(it) }
            .catch { throwable -> RoutineDayInfoUiState.Error(throwable.message ?: "Error") }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RoutineDayInfoUiState.Loading
            )
    val exerciseListState: StateFlow<ExerciseListState> =
        getAllExerciseListUseCase()
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
    fun upsertRoutineDay(routineDay: RoutineDay) {
        viewModelScope.launch {
            upsertRoutineDayUseCase(routineDay)
        }
    }
    fun deleteRoutineDay(routineDayId: Int) {
        viewModelScope.launch {
            deleteRoutineDayUseCase(routineDayId)
        }
    }
    fun upsertExercise(exerciseInfo: ExerciseInfo) {
        viewModelScope.launch {
            upsertExerciseUseCase(exerciseInfo)
        }
    }
    fun insertDayExercise(dayExercise: DayExerciseWithExercise) {
        viewModelScope.launch {
            insertDayExerciseUseCase(dayExercise)
        }
    }

    fun deleteExercise(exerciseId: Int) {
        viewModelScope.launch {
            deleteExerciseUseCase(exerciseId)
        }
    }
    fun deleteDayExercise(dayExerciseId: Int) {
        viewModelScope.launch {
            deleteDayExerciseUseCase(dayExerciseId)
        }
    }




}