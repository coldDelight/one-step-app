package com.colddelight.domain.usecase.routine

import com.colddelight.data.repository.RoutineRepository
import com.colddelight.model.Routine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodayRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    operator fun invoke(): Flow<Routine> = routineRepository.getTodayRoutine()
}