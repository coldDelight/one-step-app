package com.colddelight.model

import java.util.Date

data class Routine(
    val name: String,
    val createdTime: Date,
    val cnt: Int,
    val id: Int,
)

