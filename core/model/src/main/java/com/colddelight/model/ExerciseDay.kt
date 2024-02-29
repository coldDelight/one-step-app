package com.colddelight.model

data class ExerciseDay(
    val dayOfWeekId: Int,
    val dayOfWeek: String,
    val isRestDay: Boolean,
    val isExerciseDone: Boolean
)

