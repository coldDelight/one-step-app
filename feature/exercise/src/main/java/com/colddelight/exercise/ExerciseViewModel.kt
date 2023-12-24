package com.colddelight.exercise

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository,
) : ViewModel() {

    private val todayRoutineInfo = repository.getTodayRoutineInfo()
    private val todayExerciseList = repository.getTodayExerciseList(1, 1)

    val exerciseUiState: StateFlow<ExerciseUiState> = todayRoutineInfo
        .combine(todayExerciseList) { routine, exerciseList ->
            val curIndex = exerciseList.indexOfFirst { !it.isDone }
            ExerciseUiState.Success(routine, curIndex, exerciseList)
        }.catch {
            ExerciseUiState.Error(it.message ?: "Error")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExerciseUiState.Loading
        )

//    init {
//        viewModelScope.launch {
////            repository.addTmp()
//        }
//    }


}