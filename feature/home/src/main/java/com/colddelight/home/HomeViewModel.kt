package com.colddelight.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.ExerciseRepository2
import com.colddelight.domain.usecase.routineday.GetExerciseDayListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: ExerciseRepository2,
    getExerciseDayListUseCase: GetExerciseDayListUseCase
) : ViewModel() {

    private val todayRoutineInfo = repository.getTodayRoutineInfo()
    private val exerciseWeek = getExerciseDayListUseCase()


    val homeUiState: StateFlow<HomeUiState> = todayRoutineInfo
        .combine(exerciseWeek) { routine, week ->
            val today = LocalDate.now().dayOfWeek.value
            val isRest = week[today - 1].isRestDay
            val isDone = week[today - 1].isExerciseDone

            val state = when {
                isRest -> HomeState.Resting()
                isDone -> HomeState.Done()
                else -> HomeState.During(text = routine.name, categoryList = routine.categoryIdList)
            }

            HomeUiState.Success(
                cnt = routine.cnt,
                exerciseWeek = week,
                today = today,
                state = state
            )

        }.catch {
            HomeUiState.Error(it.message ?: "Error")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )
}
