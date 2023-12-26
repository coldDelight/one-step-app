package com.colddelight.model

data class RoutineDayInfo (
    val routineId: Int,
    val routineDayId: Int = 0,
    val dayOfWeek: Int,
    val categoryList: List<Int> = emptyList(),
    val exerciseList: List<Exercise> = emptyList()
)