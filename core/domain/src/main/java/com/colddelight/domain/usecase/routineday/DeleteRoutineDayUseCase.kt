package com.colddelight.domain.usecase.routineday

import com.colddelight.data.repository.HistoryRepository
import com.colddelight.data.repository.RoutineDayRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DeleteRoutineDayUseCase @Inject constructor(
    private val routineDayRepository: RoutineDayRepository,
    private val historyRepository: HistoryRepository,
) {
    suspend operator fun invoke(routineDayId: Int) {
        routineDayRepository.deleteRoutineDay(routineDayId)
        val todayHistory = historyRepository.getHistoryForToday().firstOrNull()
        if (todayHistory != null) {
            historyRepository.deleteHistory(todayHistory)
        }
    }
}