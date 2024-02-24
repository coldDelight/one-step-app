package com.colddelight.domain.usecase.routine

import com.colddelight.data.repository.RoutineRepository
import com.colddelight.model.Routine
import javax.inject.Inject

class UpsertRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke(routine: Routine) = routineRepository.upsertRoutine(routine)
}