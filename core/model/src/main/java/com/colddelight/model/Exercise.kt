package com.colddelight.model


sealed interface Exercise {
    val name:String
    val time:String
    val isDone:Boolean
    val exerciseId:Int
    data class Weight(
        override val name: String,
        val min: Int = 0,
        val max: Int = 0,
        override val time: String = "",
        override val exerciseId: Int,
        override val isDone :Boolean = false,
        ) : Exercise

    data class Calisthenics(
        override val name: String,
        val reps: Int,
        val set: Int,
        override val time: String = "",
        override val exerciseId: Int,
        override val isDone :Boolean = false,
        ) : Exercise
}