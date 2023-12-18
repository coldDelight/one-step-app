package com.colddelight.model

data class ExerciseInfo (
    val routineDayId: Int,
    val exerciseId: Int,
    val exerciseName: String,
    val index: Int,
    val origin: Int,
    val kgList: List<Int>,
    val repsList: List<Int>,
)