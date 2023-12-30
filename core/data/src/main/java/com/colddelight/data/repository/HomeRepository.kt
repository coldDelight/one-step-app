package com.colddelight.data.repository

import com.colddelight.model.ExerciseDay
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getExerciseWeek(): Flow<List<ExerciseDay>>
}