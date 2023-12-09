package com.colddelight.data.repository

import com.colddelight.data.util.Syncable
import com.colddelight.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository  {
    fun getExerciseResourcesStream(): Flow<List<Exercise>>

    suspend fun addItem()
}