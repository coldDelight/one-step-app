package com.colddelight.data.repository

import com.colddelight.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getRoutine(): Flow<Routine>
    suspend fun upsertRoutine(routine:Routine)

}