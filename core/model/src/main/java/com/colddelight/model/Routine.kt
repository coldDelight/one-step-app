package com.colddelight.model

data class Routine (
    val id: Int,
    val name: String,
    val cnt: Int,
    val categoryIdList: List<ExerciseCategory> = emptyList(),
)