package com.colddelight.data.repository

import com.colddelight.database.model.ExerciseEntity
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getRoutine(): Flow<Routine>

    fun getRoutineWeekInfo(): Flow<List<RoutineDayInfo>>

    suspend fun insertRoutineDay(routineDay: RoutineDayInfo)

    suspend fun insertExercise(exercise: ExerciseInfo)

    suspend fun addRoutine(): List<ExerciseEntity>

    suspend fun deleteRoutineDay(routineDayId: Int)

    fun getAllExerciseList(): Flow<List<ExerciseInfo>>
        //fun getDayExercise(rotineId: Int): List<>
}