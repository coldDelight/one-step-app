package com.colddelight.data.repository

import com.colddelight.database.model.ExerciseEntity
import com.colddelight.model.Exercise
import com.colddelight.model.TodayRoutine
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun getTodayExerciseList(routineDayId:Int):Flow<List<Exercise>>
    suspend fun getTodayRoutineInfo(): Flow<TodayRoutine>

    suspend fun addTmp()

}