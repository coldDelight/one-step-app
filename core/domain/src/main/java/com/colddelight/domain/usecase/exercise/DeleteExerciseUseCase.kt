package com.colddelight.domain.usecase.exercise

import com.colddelight.data.repository.ExerciseRepository
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(exerciseId: Int) = exerciseRepository.deleteExercise(exerciseId)
}