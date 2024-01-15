package com.colddelight.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HistoryRepository
): ViewModel() {

    private val _historyList = MutableStateFlow<List<LocalDate>>(emptyList())
    val historyList: StateFlow<List<LocalDate>> get() = _historyList

    init {
        //viewModelScope.launch { repository.insertHistory() }
        viewModelScope.launch{ Log.e("TAG", "모든 기록 ${repository.getAllDoneHistory().first()}: ", )}
        val currentDate = LocalDate.now()
        val firstDayOfMonth = currentDate.with(TemporalAdjusters.firstDayOfMonth())
        Log.e("TAG", ": $firstDayOfMonth", )
        getHistoryForSelectedMonth(firstDayOfMonth)
    }

    fun getHistoryForSelectedMonth(selectedMonth: LocalDate) {
        viewModelScope.launch {
            repository.getHistoryForSelectedMonth(selectedMonth)
                .collect { history ->
                    Log.e("TAG", "getHistoryForSelectedMonth: $history", )
                    _historyList.value = history
                }
        }
    }
}