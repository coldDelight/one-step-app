package com.colddelight.data.repository

import com.colddelight.model.DayExercise
import com.colddelight.model.DayExerciseUI
import kotlinx.coroutines.flow.Flow

interface DayExerciseRepository {
    fun getDayExerciseList(): Flow<List<DayExerciseUI>>

    suspend fun deleteDayExercise(dayExerciseId: Int)

    suspend fun insertDayExercise(dayExercise: DayExercise):Long

}