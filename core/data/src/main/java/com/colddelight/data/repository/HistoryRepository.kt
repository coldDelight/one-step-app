package com.colddelight.data.repository

import com.colddelight.database.model.HistoryEntity
import com.colddelight.model.DayHistory
import com.colddelight.model.Exercise
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HistoryRepository {
    fun getHistoryForToday(): Flow<Int>
    suspend fun deleteHistory(historyId: Int)
    fun getHistoryDateForThisWeek(): Flow<List<LocalDate>>

    //------------------------
    fun getHistoryForSelectedMonth(selectedDate: LocalDate): Flow<List<DayHistory>>

    fun getHistoryForSelectedDay(historyId: Int): Flow<List<Exercise>>

    fun getAllDoneHistory(): Flow<List<LocalDate>>


}