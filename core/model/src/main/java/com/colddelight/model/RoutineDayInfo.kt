package com.colddelight.model

data class RoutineDayInfo (
    val routineId: Int?,
    val routineDayId: Int?,
    val dayOfWeek: Int,
    val categoryList: List<Int>?,
    val exerciseList: List<Exercise>?
)