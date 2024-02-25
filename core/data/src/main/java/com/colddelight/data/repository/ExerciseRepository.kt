package com.colddelight.data.repository

import com.colddelight.model.ExerciseInfo
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun upsertExercise(exercise: ExerciseInfo)

    suspend fun deleteExercise(exerciseId: Int)

    fun getAllExerciseList(): Flow<List<ExerciseInfo>>

}