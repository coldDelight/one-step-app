package com.colddelight.model

enum class ExerciseCategory(val id: Int) {
    CHEST(1),
    SHOULDER(2),
    BACK(3),
    ARM(4),
    LEG(5),
    CALISTHENICS(6);

    companion object {
        fun fromId(id: Int): ExerciseCategory? =
            values().find { it.id == id }
    }
}