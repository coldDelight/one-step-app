package com.colddelight.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.RoutineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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

    }

    val routineUiState: StateFlow<RoutineUiState> =
        repository.getRoutine()
            .map {  RoutineUiState.Success(it) }
            .catch{ throwable -> throwable.message?.let { RoutineUiState.Error(it) } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue =  RoutineUiState.Loading
            )



}