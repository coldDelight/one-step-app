package com.colddelight.data.repository

import com.colddelight.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getExerciseResourcesStream(): Flow<List<Exercise>>

    suspend fun addItem()
    suspend fun addRoutine()
    suspend fun addRoutine(id:Int)
    suspend fun delRoutine(id:Int)
    suspend fun sync(id:Int)
}