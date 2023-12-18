package com.colddelight.data.repository

import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getRoutine(): Flow<Routine>

    fun getRoutineWeekInfo(): Flow<List<RoutineDayInfo>>

    //fun getDayExercise(rotineId: Int): List<>
}