package com.colddelight.exercisedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.ExerciseRepository
import com.colddelight.designsystem.component.SetAction
import com.colddelight.exercise.ExerciseDetailUiState
import com.colddelight.exercise.ExerciseUiState
import com.colddelight.model.Exercise
import com.colddelight.model.SetInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class ExerciseDetailViewModel @Inject constructor(
//
//) : ViewModel() {
//    private val _exerciseDetailUiState =
//        MutableStateFlow<ExerciseDetailUiState>(ExerciseDetailUiState.Default(0))
//    val exerciseDetailUiState: StateFlow<ExerciseDetailUiState> = _exerciseDetailUiState
//
//
////    init {
////        repository.getTodayExerciseList(exerciseId)
////    }
//
//    fun updateDetailUiState(newState: ExerciseDetailUiState) {
//        when (newState) {
//            is ExerciseDetailUiState.Default -> {}
//            is ExerciseDetailUiState.During -> {}
//            is ExerciseDetailUiState.Resting -> {}
//            is ExerciseDetailUiState.Done -> {}
//        }
//        _exerciseDetailUiState.value = newState
//    }
//
//}