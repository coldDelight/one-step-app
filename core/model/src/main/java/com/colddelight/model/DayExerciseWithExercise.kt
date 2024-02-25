package com.colddelight.model

data class DayExerciseWithExercise(
   val routineDayId: Int,
   val exerciseId: Int,
   val kgList: List<Int>,
   val repsList: List<Int>,
   val id: Int,
   val name:String="",
   val category:ExerciseCategory=ExerciseCategory.CHEST,
)
