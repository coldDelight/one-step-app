package com.colddelight.model


sealed interface Exercise {
    val name: String
    val isDone: Boolean
    val exerciseId: Int
    val setInfoList: List<SetInfo>
    val category: ExerciseCategory

    data class Weight(
        override val name: String,
        val min: Int = 0,
        val max: Int = 0,
        override val exerciseId: Int,
        override val isDone: Boolean = false,
        override val setInfoList: List<SetInfo> = listOf(),
        override val category: ExerciseCategory = ExerciseCategory.CHEST,
    ) : Exercise

    data class Calisthenics(
        override val name: String,
        val reps: Int,
        val set: Int,
        override val exerciseId: Int,
        override val isDone: Boolean = false,
        override val setInfoList: List<SetInfo> = listOf(),
        override val category: ExerciseCategory = ExerciseCategory.CHEST,

    ) : Exercise
}

data class SetInfo(
    val kg: Int,
    val reps: Int
)