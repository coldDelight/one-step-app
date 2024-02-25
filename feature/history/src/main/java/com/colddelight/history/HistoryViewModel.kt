package com.colddelight.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.HistoryRepository
import com.colddelight.model.DayHistory
import com.colddelight.model.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HistoryRepository
): ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()))
    private val selectedDate: StateFlow<LocalDate> get() = _selectedDate

    private val _dayHistoryExerciseList2 = MutableStateFlow(emptyList<Exercise>())
    val dayHistoryExerciseList: StateFlow<List<Exercise>> get() = _dayHistoryExerciseList2


    val historyUiState: StateFlow<HistoryUiState> =
        selectedDate
            .flatMapLatest { selectedMonth ->
                repository.getHistoryForSelectedMonth(selectedMonth)
                    .map { HistoryUiState.Success(it) }
                    .catch { throwable -> HistoryUiState.Error(throwable.message ?: "Error") }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HistoryUiState.Loading
            )

    fun upDateMonth(selectedMonth: LocalDate){
        _selectedDate.value = selectedMonth
    }

    fun getDayHistoryExercise(historyId: Int){
        viewModelScope.launch {
            val exercises = repository.getHistoryForSelectedDay(historyId).first()
            _dayHistoryExerciseList2.value = exercises
        }
    }

}

sealed interface HistoryUiState {
    data object Loading : HistoryUiState

    data class Error(val msg: String) : HistoryUiState

    data class Success(val historyList: List<DayHistory>) : HistoryUiState
}