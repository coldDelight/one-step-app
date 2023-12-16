package com.colddelight.model

data class HistoryExercise(
    val historyId: Int,
    val exerciseId: Int,
    val index: Int,
    val origin: Int,
    val kgList: List<Int>,
    val repsList: List<Int>,
    val id: Int,
)

