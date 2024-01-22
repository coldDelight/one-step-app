package com.colddelight.exercise

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.ExerciseRepository
import com.colddelight.designsystem.component.SetAction
import com.colddelight.model.Exercise
import com.colddelight.model.SetInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository,
) : ViewModel() {

    private val todayRoutineInfo = repository.getTodayRoutineInfo()
    private val todayExerciseList = repository.getTodayExerciseList()

    private val _exerciseDetailUiState =
        MutableStateFlow<ExerciseDetailUiState>(ExerciseDetailUiState.Default(0))
    val exerciseDetailUiState: StateFlow<ExerciseDetailUiState> = _exerciseDetailUiState


    private val _exerciseUiState =
        MutableStateFlow<ExerciseUiState>(ExerciseUiState.Loading)
    val exerciseUiState: StateFlow<ExerciseUiState> = _exerciseUiState


    init {
        viewModelScope.launch {
            repository.initExercise()
            todayRoutineInfo.combine(todayExerciseList) { routine, exerciseList ->
                val curIndex = exerciseList.filter { it.isDone }.size
                Log.e("TAG", "결과는 무엇인가${exerciseList}: ")
                ExerciseUiState.Success(routine, curIndex, exerciseList)
            }.collectLatest {
                _exerciseUiState.value = it
            }
        }
    }

    fun finExercise() {
        viewModelScope.launch {
            repository.finHistory()
        }
    }

    fun setDone() {
        val exerciseState = exerciseUiState.value as ExerciseUiState.Success
        val cur = exerciseState.curIndex
        viewModelScope.launch {

            repository.updateDayExercise(
                exerciseState.exerciseList[cur].dayExerciseId,
                exerciseState.exerciseList[cur].setInfoList
            )
            repository.updateHistoryExercise(exerciseState.exerciseList[cur].exerciseId, true)

        }
    }

    fun updateDetailUiState(newState: ExerciseDetailUiState) {
        val curExercise = (exerciseUiState.value as ExerciseUiState.Success)
        val cur = curExercise.curIndex
        val maxIndex = curExercise.exerciseList[cur].setInfoList.size

        if (newState.curSet == maxIndex) {
            viewModelScope.launch {
                _exerciseDetailUiState.value = ExerciseDetailUiState.Done(maxIndex)
            }
        } else {
            _exerciseDetailUiState.value = newState
        }
    }

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
                if (index >= toChange) updatedKg
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
                if (index >= toChange) updatedReps
                else setInfo.reps
            }
            viewModelScope.launch {
                repository.upDateRepsList(exercise.exerciseId, repsList)
            }
        }
    }

    private fun deleteSet(exercise: Exercise, toChange: Int) {
        if (exercise.setInfoList.size != 1) {
            val setInfoList = exercise.setInfoList.filterIndexed { index, _ -> index != toChange }
            viewModelScope.launch {
                repository.upDateSetInfo(
                    exercise.exerciseId,
                    setInfoList.map { it.kg },
                    setInfoList.map { it.reps })
            }
        }
    }

    private fun addSet(exercise: Exercise) {
        val setInfoList = exercise.setInfoList.toMutableList()
        setInfoList.add(SetInfo(20, 12))
        viewModelScope.launch {
            repository.upDateSetInfo(
                exercise.exerciseId,
                setInfoList.map { it.kg },
                setInfoList.map { it.reps })
        }
    }
}

