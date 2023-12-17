package com.colddelight.model

data class TodayRoutine(
    val name: String,
    val cnt: Int,
    val categoryIdList: List<ExerciseCategory>,
)