package com.colddelight.network.model

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class NetworkHistory(
    val id: Int = 0,
    val room_id: Int,
    val uuid: String = "",
    val created_time: String,
    val category_list: String,
    val total_time: String,
    val isDone: Boolean,
    val isFree: Boolean,
    val routine_id: Int
)

