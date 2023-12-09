package com.colddelight.network.datasourceImpl

import com.colddelight.network.SupabaseClient
import com.colddelight.network.datasource.ExerciseDataSource
import com.colddelight.network.model.NetworkExercise
import io.github.jan.supabase.postgrest.postgrest

class ExerciseDataSourceImpl(): ExerciseDataSource {
    override suspend fun getExercise(): List<NetworkExercise> {
        return SupabaseClient.client.postgrest["exercise"].select().decodeList<NetworkExercise>()

    }
}