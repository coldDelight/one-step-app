package com.colddelight.exercise

import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.ExerciseRepository
import com.colddelight.model.Exercise
import com.colddelight.model.SetInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository,
) : ViewModel() {

    private val todayRoutineInfo = repository.getTodayRoutineInfo()
    private val todayExerciseList = repository.getTodayExerciseList(1, 1)

    private val _exerciseDetailUiState =
        MutableStateFlow<ExerciseDetailUiState>(ExerciseDetailUiState.Default)
    val exerciseDetailUiState: StateFlow<ExerciseDetailUiState> = _exerciseDetailUiState

    fun updateDetailUiState(newState: ExerciseDetailUiState) {
        when (newState) {
            is ExerciseDetailUiState.Default -> {}
            is ExerciseDetailUiState.During -> {}
            is ExerciseDetailUiState.Resting -> {}
            is ExerciseDetailUiState.Done -> {}
        }
        _exerciseDetailUiState.value = newState
    }

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


    fun performSetAction(action: SetAction) {
        when (val current = exerciseUiState.value) {
            is ExerciseUiState.Success -> {
                val exercise = current.exerciseList[current.curIndex]
                when (action) {
                    is SetAction.UpdateKg ->
                        upDateKgList(exercise, action.updatedKg, action.toChange)

                    is SetAction.UpdateReps ->
                        upDateRepsList(exercise, action.updatedReps, action.toChange)

                    is SetAction.DeleteSet ->
                        deleteSet(exercise, action.toChange)

                    is SetAction.AddSet ->
                        addSet(exercise)
                }
            }

            else -> {}
        }
    }


    private fun upDateKgList(exercise: Exercise, updatedKg: Int, toChange: Int) {
        if (updatedKg > 0) {
            val kgList = exercise.setInfoList.mapIndexed { index, setInfo ->
                if (index == toChange) updatedKg
                else setInfo.kg
            }
            viewModelScope.launch {
                repository.upDateKgList(exercise.exerciseId, kgList)
            }
        }
    }

    private fun upDateRepsList(exercise: Exercise, updatedReps: Int, toChange: Int) {
        if (updatedReps > 0) {
            val repsList = exercise.setInfoList.mapIndexed { index, setInfo ->
                if (index == toChange) updatedReps
                else setInfo.reps
            }
            viewModelScope.launch {
                repository.upDateRepsList(exercise.exerciseId, repsList)
            }
        }
    }

    private fun deleteSet(exercise: Exercise, toChange: Int) {
        val setInfoList =
            exercise.setInfoList.filterIndexed { index, _ -> index != toChange }
        viewModelScope.launch {
            repository.upDateRepsList(exercise.exerciseId, setInfoList.map { it.reps })
            repository.upDateKgList(exercise.exerciseId, setInfoList.map { it.kg })
        }
    }

    private fun addSet(exercise: Exercise) {
        val setInfoList =
            exercise.setInfoList.toMutableList()
        setInfoList.add(SetInfo(20, 12))
        viewModelScope.launch {
            repository.upDateRepsList(exercise.exerciseId, setInfoList.map { it.reps })
            repository.upDateKgList(exercise.exerciseId, setInfoList.map { it.kg })
        }
    }
}

sealed class SetAction {
    data class UpdateKg(val updatedKg: Int, val toChange: Int) : SetAction()
    data class UpdateReps(val updatedReps: Int, val toChange: Int) : SetAction()
    data class DeleteSet(val toChange: Int) : SetAction()
    data object AddSet : SetAction()
}