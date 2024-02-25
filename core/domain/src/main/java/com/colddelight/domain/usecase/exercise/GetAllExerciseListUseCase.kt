package com.colddelight.domain.usecase.exercise

import com.colddelight.data.repository.ExerciseRepository
import javax.inject.Inject

class GetAllExerciseListUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    operator fun invoke() = exerciseRepository.getAllExerciseList()
}