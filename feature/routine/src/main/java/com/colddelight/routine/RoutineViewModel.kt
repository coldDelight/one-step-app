package com.colddelight.routine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.RoutineRepository
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.RoutineDayInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
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

    init {
        viewModelScope.launch {
            Log.e("TAG", "insertRoutineDay: 운동목록${repository.getAllExerciseList().first()}", )
            //repository.addRoutine()
            //Log.e("부모델", "${repository.addRoutine()}: ", )
            //Log.e("부모델", "${repository.check()}: ", )
        }
    }


    val exerciseListState: StateFlow<ExerciseListState> =
        repository.getAllExerciseList()
            .map { ExerciseListState.NotNone(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ExerciseListState.None
            )

    fun insertRoutineDay(routineDayInfo: RoutineDayInfo){
        viewModelScope.launch {
            repository.insertRoutineDay(routineDayInfo)
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
