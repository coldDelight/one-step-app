package com.colddelight.model

sealed interface Exercise {
    val name: String
    val exerciseId: Int
    val setInfoList: List<SetInfo>
    val category: ExerciseCategory
    val dayExerciseId: Int

    data class Weight(
        override val name: String,
        val min: Int,
        val max: Int,
        override val exerciseId: Int,
        override val setInfoList: List<SetInfo>,
        override val category: ExerciseCategory,
        override val dayExerciseId: Int,

    ) : Exercise

    data class Calisthenics(
        override val name: String,
        val reps: Int,
        val set: Int,
        override val exerciseId: Int,
        override val setInfoList: List<SetInfo>,
        override val category: ExerciseCategory,
        override val dayExerciseId: Int
    ) : Exercise
}

data class SetInfo(
    val kg: Int,
    val reps: Int
)