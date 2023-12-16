package com.colddelight.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkRoutineDay(
    val id: Int = 0,
    val room_id: Int,
    val category_list: String,
    val routine_id: Int,
    val day_of_week: Int
)


