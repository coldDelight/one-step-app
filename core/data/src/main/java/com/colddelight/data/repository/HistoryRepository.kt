package com.colddelight.data.repository

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HistoryRepository {
    fun getHistoryForSelectedMonth(selectedMonth: LocalDate): Flow<List<LocalDate>>

    fun getAllDoneHistory(): Flow<List<LocalDate>>

    suspend fun insertHistory()

}