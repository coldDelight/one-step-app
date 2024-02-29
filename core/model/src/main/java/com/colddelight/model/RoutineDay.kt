package com.colddelight.model

data class RoutineDay (
    val id: Int = 0,
    val routineId: Int,
    val dayOfWeek: Int,
    val categoryList: List<Int> = emptyList(),
    val exerciseList: List<DayExerciseUI> = emptyList()
)