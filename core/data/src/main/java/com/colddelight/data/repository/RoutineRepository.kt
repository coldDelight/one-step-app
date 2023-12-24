package com.colddelight.data.repository

import com.colddelight.database.model.DayExerciseEntity
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.RoutineDayEntity
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getRoutine(): Flow<Routine>

    fun getRoutineWeekInfo(): Flow<List<RoutineDayInfo>>

    suspend fun insertRoutineDay(routineDay: RoutineDayInfo)
    suspend fun addRoutine(): List<ExerciseEntity>

    fun getAllExerciseList(): Flow<List<ExerciseInfo>>
        //fun getDayExercise(rotineId: Int): List<>
}