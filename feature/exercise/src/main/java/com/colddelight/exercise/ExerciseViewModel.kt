package com.colddelight.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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
            repository.addRoutine()
        }
        getRoutineInfo()
    }

    private fun getRoutineInfo() {
        viewModelScope.launch {
            try {
                val routineInfo = repository.getTodayRoutineInfo().first()
                //check
                _exerciseUiState.value = ExerciseUiState.Success(routineInfo, 0, listOf())
            } catch (e: Exception) {
                _exerciseUiState.value = ExerciseUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}