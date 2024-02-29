package com.colddelight.exercise

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.ExerciseRepository2
import com.colddelight.designsystem.component.ui.SetAction
import com.colddelight.domain.usecase.routine.GetRoutineUseCase
import com.colddelight.domain.usecase.routine.GetTodayRoutineUseCase
import com.colddelight.domain.usecase.routine.UpsertRoutineUseCase
import com.colddelight.model.Exercise
import com.colddelight.model.SetInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getRoutineUseCase: GetRoutineUseCase,
    private val repository: ExerciseRepository2,
    private val upsertRoutineUseCase: UpsertRoutineUseCase,
    getTodayRoutineUseCase: GetTodayRoutineUseCase

) : ViewModel() {

    private val todayRoutineInfo = getTodayRoutineUseCase()
    private val todayExerciseList = repository.getTodayExerciseList()

    private val _exerciseDetailUiState =
        MutableStateFlow<ExerciseDetailUiState>(ExerciseDetailUiState.Default(0))
    val exerciseDetailUiState: StateFlow<ExerciseDetailUiState> = _exerciseDetailUiState


    private val _exerciseUiState =
        MutableStateFlow<ExerciseUiState>(ExerciseUiState.Loading)
    val exerciseUiState: StateFlow<ExerciseUiState> = _exerciseUiState


    init {
        try {
            viewModelScope.launch {
                repository.initExercise()
                todayRoutineInfo.combine(todayExerciseList) { routine, exerciseList ->
                    val curIndex = exerciseList.filter { it.isDone }.size
//                    val curIndex = exerciseList.size
                    ExerciseUiState.Success(routine, curIndex, exerciseList)
                }.collectLatest {
                    _exerciseUiState.value = it
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "ddddddddddddd$e: ")
        }

    }

    fun finExercise() {
        viewModelScope.launch {
            repository.finHistory()
            val routine = getRoutineUseCase().first()
            upsertRoutineUseCase(routine.copy(cnt = routine.cnt + 1))
        }
    }

    fun setDone() {
        val exerciseState = exerciseUiState.value as ExerciseUiState.Success
        val cur = exerciseState.curIndex
        viewModelScope.launch {

            repository.updateDayExercise(
                exerciseState.exerciseList[cur].exercise.exerciseId,
                exerciseState.exerciseList[cur].exercise.setInfoList
            )
            repository.updateHistoryExercise(
                exerciseState.exerciseList[cur].exercise.exerciseId,
                true
            )

        }
    }

    fun updateDetailUiState(newState: ExerciseDetailUiState) {
        val curExercise = (exerciseUiState.value as ExerciseUiState.Success)
        val cur = curExercise.curIndex
        val maxIndex = curExercise.exerciseList[cur].exercise.setInfoList.size

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
                        upDateKgList(exercise.exercise, action.updatedKg, action.toChange)

                    is SetAction.UpdateReps ->
                        upDateRepsList(exercise.exercise, action.updatedReps, action.toChange)

                    is SetAction.DeleteSet ->
                        deleteSet(exercise.exercise, action.toChange)

                    is SetAction.AddSet ->
                        addSet(exercise.exercise)
                }
            }

            else -> {}
        }
    }

    private fun upDateKgList(exercise: Exercise, updatedKg: Int, toChange: Int) {
        if (updatedKg >= 0) {
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
        if (updatedReps >= 0) {
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

