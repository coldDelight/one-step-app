package com.colddelight.data.repository

import com.colddelight.model.RoutineDay
import kotlinx.coroutines.flow.Flow

interface RoutineDayRepository {
    suspend fun upsertRoutineDay(routineDay: RoutineDay)
    suspend fun deleteRoutineDay(routineDayId: Int)
    fun getRoutineDayList(): Flow<List<RoutineDay>>
    fun getExerciseDayList(): Flow<List<RoutineDay>>
}