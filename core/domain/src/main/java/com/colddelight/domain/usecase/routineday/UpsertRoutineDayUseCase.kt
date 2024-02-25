package com.colddelight.domain.usecase.routineday

import com.colddelight.data.repository.RoutineDayRepository
import com.colddelight.model.RoutineDay
import javax.inject.Inject

class UpsertRoutineDayUseCase @Inject constructor(
    private val routineDayRepository: RoutineDayRepository
) {
    suspend operator fun invoke(routineDay: RoutineDay) = routineDayRepository.upsertRoutineDay(routineDay)
}