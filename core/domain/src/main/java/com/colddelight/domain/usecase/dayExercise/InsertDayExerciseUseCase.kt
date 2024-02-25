package com.colddelight.domain.usecase.dayExercise

import com.colddelight.data.repository.DayExerciseRepository
import com.colddelight.data.repository.HistoryExerciseRepository
import com.colddelight.data.repository.HistoryRepository
import com.colddelight.model.DayExerciseWithExercise
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class InsertDayExerciseUseCase @Inject constructor(
    private val dayExerciseRepository: DayExerciseRepository,
    private val historyRepository: HistoryRepository,
    private val historyExerciseRepository: HistoryExerciseRepository

) {
    suspend operator fun invoke(dayExerciseWithExercise: DayExerciseWithExercise) {

        val id = dayExerciseRepository.insertDayExercise(dayExerciseWithExercise).toInt()
        val todayHistoryId = historyRepository.getHistoryForToday().firstOrNull()
        if (todayHistoryId != null) {
            historyExerciseRepository.insertHistoryExercise(id,todayHistoryId,dayExerciseWithExercise)
        }
    }
}