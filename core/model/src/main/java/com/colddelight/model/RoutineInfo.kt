package com.colddelight.model

data class RoutineInfo(
    val name: String,
    val cnt: Int,
    val categoryIdList: List<ExerciseCategory>,
)