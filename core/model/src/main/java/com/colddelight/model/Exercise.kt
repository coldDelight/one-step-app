package com.colddelight.model


sealed interface Exercise {
    data class Weight(
        val name: String,
        val min: Int = 0,
        val max: Int = 0,
        val time: String = "",
        val exerciseId: Int,
        val isDone :Boolean = false,
        ) : Exercise

    data class Calisthenics(
        val name: String,
        val reps: Int,
        val set: Int,
        val time: String = "",
        val exerciseId: Int,
        val isDone :Boolean = false,
        ) : Exercise
}