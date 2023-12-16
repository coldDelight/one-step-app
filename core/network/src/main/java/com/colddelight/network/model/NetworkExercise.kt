package com.colddelight.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkExercise(
    val id: Int = 0,
    val room_id: Int,
    val category: String,
    val name: String,
    val uuid: String = ""
)