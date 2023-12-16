package com.colddelight.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkHistoryExercise(
    val id: Int = 0,
    val room_id: Int,
    val exercise_id: Int,
    val index: Int,
    val origin: Int,
    val history_id: Int,
    val kg_list: String,
    val reps_list: String,
)
