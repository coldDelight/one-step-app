package com.colddelight.network.model

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class NetworkBodyWeight(
    val id: Int = 0,
    val room_id: Int,
    val created_time: String,
    val weight: Int,
    val uuid: String = ""
)