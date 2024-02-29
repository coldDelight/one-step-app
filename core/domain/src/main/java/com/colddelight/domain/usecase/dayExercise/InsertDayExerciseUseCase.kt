package com.colddelight.domain.usecase.dayExercise

import com.colddelight.data.repository.DayExerciseRepository
import com.colddelight.data.repository.HistoryExerciseRepository
import com.colddelight.data.repository.HistoryRepository
import com.colddelight.model.DayExercise
import com.colddelight.model.HistoryExercise
import com.colddelight.model.HistoryExerciseUI
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class InsertDayExerciseUseCase @Inject constructor(
    private val dayExerciseRepository: DayExerciseRepository,
    private val historyRepository: HistoryRepository,
    private val historyExerciseRepository: HistoryExerciseRepository

) {
    suspend operator fun invoke(dayExercise: DayExercise) {

        val id = dayExerciseRepository.insertDayExercise(dayExercise).toInt()
        val todayHistoryId = historyRepository.getHistoryForToday().firstOrNull()
        if (todayHistoryId != null) {
            historyExerciseRepository.insertHistoryExercise(
                HistoryExercise(
                    id = 0,
                    historyId = todayHistoryId,
                    exerciseId = dayExercise.exerciseId,
                    dayExerciseId = id,
                    isDone = false,
                    kgList = dayExercise.kgList,
                    repsList = dayExercise.repsList,
                )
            )
        }
    }
}