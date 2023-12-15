package com.colddelight.network.datasource

import com.colddelight.network.model.NetworkExercise
import com.colddelight.network.model.User

interface ExerciseDataSource {
    suspend fun getExercise(): List<NetworkExercise>
    suspend fun addExercise(networkExercise:NetworkExercise)

}