package com.colddelight.data.repository

import com.colddelight.model.RoutineInfo
import kotlinx.coroutines.flow.Flow


interface RoutineRepository {

    fun getTodayRoutineInfo(): Flow<RoutineInfo>
    suspend fun addRoutine()

}