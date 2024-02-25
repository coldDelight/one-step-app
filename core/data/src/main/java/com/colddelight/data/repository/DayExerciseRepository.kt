package com.colddelight.data.repository

import com.colddelight.model.DayExerciseWithExercise
import kotlinx.coroutines.flow.Flow

interface DayExerciseRepository {
    fun getDayExerciseList(): Flow<List<DayExerciseWithExercise>>

    suspend fun deleteDayExercise(dayExerciseId: Int)

    suspend fun insertDayExercise(dayExerciseWithExercise: DayExerciseWithExercise):Long

}