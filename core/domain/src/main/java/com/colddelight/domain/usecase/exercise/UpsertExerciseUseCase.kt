package com.colddelight.domain.usecase.exercise

import com.colddelight.data.repository.ExerciseRepository
import com.colddelight.model.ExerciseInfo
import javax.inject.Inject

class UpsertExerciseUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(exercise: ExerciseInfo) = exerciseRepository.upsertExercise(exercise)
}