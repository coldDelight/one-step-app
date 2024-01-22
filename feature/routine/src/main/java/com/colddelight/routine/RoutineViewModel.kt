package com.colddelight.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.RoutineRepository
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
    private val repository: RoutineRepository,
    ): ViewModel() {
    //private val _routineUiState = MutableStateFlow<RoutineUiState>(RoutineUiState.Loading)
    //val routineUiState: StateFlow<RoutineUiState> = _routineUiState

    val exerciseListState: StateFlow<ExerciseListState> =
        repository.getAllExerciseList()
            .map { ExerciseListState.NotNone(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ExerciseListState.None
            )

    fun insertRoutine(routine: Routine){
        viewModelScope.launch {
            repository.insertRoutine(routine)
        }
    }

    fun insertRoutineDay(routineDayInfo: RoutineDayInfo){
        viewModelScope.launch {
            repository.insertRoutineDay(routineDayInfo)
        }
    }

    fun insertExercise(exerciseInfo: ExerciseInfo){
        viewModelScope.launch {
            repository.insertExercise(exerciseInfo)
        }
    }

    fun insertDayExercise(dayExercise: DayExercise){
        viewModelScope.launch {
            repository.insertDayExercise(dayExercise)
        }
    }

    fun deleteRoutineDay(routineDayId: Int){
        viewModelScope.launch {
            repository.deleteRoutineDay(routineDayId)
        }
    }

    fun deleteExercise(exerciseId: Int){
        viewModelScope.launch {
            repository.deleteExercise(exerciseId)
        }
    }

    fun deleteDayExercise(dayExerciseId: Int){
        viewModelScope.launch {
            repository.deleteDayExercise(dayExerciseId)
        }
    }


    val routineInfoUiState: StateFlow<RoutineInfoUiState> =
        repository.getRoutine()
            .map {  RoutineInfoUiState.Success(it) }
            .catch{ throwable -> RoutineInfoUiState.Error(throwable.message?:"Error")  }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue =  RoutineInfoUiState.Loading
            )

    val routineDayInfoUiState: StateFlow<RoutineDayInfoUiState> =
        repository.getRoutineWeekInfo()
            .map {  RoutineDayInfoUiState.Success(it) }
            .catch{ throwable -> RoutineDayInfoUiState.Error(throwable.message?:"Error")  }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue =  RoutineDayInfoUiState.Loading
            )

}
