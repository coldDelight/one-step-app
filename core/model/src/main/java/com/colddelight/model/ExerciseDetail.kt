package com.colddelight.model

data class ExerciseDetail (
    val routineDayId: Int,
    val exerciseId: Int,
    val name: String,
    val category: ExerciseCategory,
    val index: Int,
    val origin: Int,
    val kgList: List<Int>,
    val repsList: List<Int>,
)