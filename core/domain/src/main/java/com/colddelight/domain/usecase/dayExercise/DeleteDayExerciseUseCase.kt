package com.colddelight.domain.usecase.dayExercise

import com.colddelight.data.repository.DayExerciseRepository
import com.colddelight.data.repository.HistoryExerciseRepository
import com.colddelight.data.repository.HistoryRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


class DeleteDayExerciseUseCase @Inject constructor(
    private val dayExerciseRepository: DayExerciseRepository,
    private val historyRepository: HistoryRepository,
    private val historyExerciseRepository: HistoryExerciseRepository

) {
    suspend operator fun invoke(dayExerciseId: Int) {
        dayExerciseRepository.deleteDayExercise(dayExerciseId)
        val todayHistoryId = historyRepository.getHistoryForToday().firstOrNull()
        if (todayHistoryId != null) {
            historyExerciseRepository.deleteHistoryExercise(dayExerciseId)
        }
    }
}