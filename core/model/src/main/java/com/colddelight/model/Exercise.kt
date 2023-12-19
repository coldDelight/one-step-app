package com.colddelight.model


sealed interface Exercise {
    data class Weight(
        val isDone :Boolean,
        val name: String,
        val min: Int = 0,
        val max: Int = 0,
        val time: String = "",
        val exerciseId: Int
    ) : Exercise

    data class Calisthenics(
        val isDone :Boolean,
        val name: String,
        val reps: Int,
        val set: Int,
        val time: String = "",
        val exerciseId: Int
    ) : Exercise
}