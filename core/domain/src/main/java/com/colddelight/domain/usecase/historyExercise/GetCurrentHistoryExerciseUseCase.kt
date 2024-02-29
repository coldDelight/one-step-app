package com.colddelight.domain.usecase.historyExercise

import com.colddelight.data.repository.ExerciseRepository
import com.colddelight.data.repository.HistoryRepository
import com.colddelight.data.repository.RoutineDayRepository
import javax.inject.Inject

class GetCurrentHistoryExerciseUseCase @Inject constructor(
    private val routineDayRepository: RoutineDayRepository,
) {
    operator fun invoke() {

    }
}