package com.colddelight.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkRoutine(
    val id: Int = 0,
    val room_id: Int,
    val name: String,
    val cnt: Int,
    val created_time: String,
    val uuid: String = ""
)

