package com.colddelight.model

data class FreeExercise(
    val exerciseId: Int,
    val historyId: Int,
    val index: Int,
    val origin: Int,
    val kgList: List<Int>,
    val repsList: List<Int>,
    val id: Int,
)
