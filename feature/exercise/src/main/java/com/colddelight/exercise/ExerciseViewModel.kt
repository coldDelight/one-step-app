package com.colddelight.exercise

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository,
) : ViewModel() {

    private val _exerciseUiState = MutableStateFlow<ExerciseUiState>(ExerciseUiState.Loading)
    val exerciseUiState: StateFlow<ExerciseUiState> = _exerciseUiState

    init {
        viewModelScope.launch {
            repository.addTmp()

        }
        getTodayRoutine()
    }

    private fun getTodayRoutine() {
        viewModelScope.launch {
            val routineFlow = repository.getTodayRoutineInfo()
            val exerciseListFlow = repository.getTodayExerciseList(1)
            routineFlow.combine(exerciseListFlow) { routine, exerciseList ->
                Log.e("TAG", "getTodayRoutine: $exerciseList", )
                ExerciseUiState.Success(routine, 0, exerciseList)
            }.catch {
                _exerciseUiState.value = ExerciseUiState.Error(it.message ?: "error")
            }.collect { uiState ->
                _exerciseUiState.value = uiState
            }
        }
    }

}