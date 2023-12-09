package com.colddelight.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkExercise(
    val id: Int = 0,
    val category_id: Int = 0,
    val name: String,
    val uuid:String
)