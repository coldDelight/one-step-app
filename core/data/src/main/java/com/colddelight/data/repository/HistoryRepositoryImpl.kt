package com.colddelight.data.repository

import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.model.HistoryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    ): HistoryRepository {

    override fun getHistoryForSelectedMonth(selectedMonth: LocalDate): Flow<List<LocalDate>> {
        return historyDao.getHistoryForSelectedMonth(selectedMonth)
    }

    override fun getAllDoneHistory(): Flow<List<LocalDate>> {
        return historyDao.getAllDoneHistory()
    }

    override suspend fun insertHistory() {
        historyDao.insertHistory(
            HistoryEntity(
                LocalDate.of(2024,1,3),
                listOf(1, 2),
                isDone = true,
            )
        )
        historyDao.insertHistory(
            HistoryEntity(
                LocalDate.of(2024,1,10),
                listOf(1, 2),
                isDone = true,
            )
        )
    }
}