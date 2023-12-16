package com.colddelight.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkDayExercise(
    val id: Int = 0,
    val room_id: Int,
    val kg_list: String,
    val reps_list: String,
    val index: Int,
    val origin: Int,
    val exercise_id: Int,
    val routine_day_id: Int,
)

