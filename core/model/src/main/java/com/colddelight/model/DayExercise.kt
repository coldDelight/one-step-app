package com.colddelight.model

data class DayExercise(

    val routineDayId: Int,
    val exerciseId: Int,
    val index: Int,
    val origin: Int,
    val kgList: List<Int>,
    val repsList: List<Int>,
    val id: Int,

)

