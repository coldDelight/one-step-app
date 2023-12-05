package com.colddelight.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int = 0,
    val uuid: String = "",
//    @SerialName("current_body_weight")
    val current_body_weight: Int? = 0,
//    @SerialName("current_routine_id")
    val current_routine_id: Int? = 0,
)