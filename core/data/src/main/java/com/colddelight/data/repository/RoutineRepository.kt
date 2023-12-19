package com.colddelight.data.repository

import com.colddelight.database.model.DayExerciseEntity
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.RoutineDayEntity
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getRoutine(): Flow<Routine>

    fun getRoutineWeekInfo(): Flow<List<RoutineDayInfo>>
    suspend fun addRoutine(): List<ExerciseEntity>

    suspend fun check(): Set<DayExerciseEntity>

        //fun getDayExercise(rotineId: Int): List<>
}