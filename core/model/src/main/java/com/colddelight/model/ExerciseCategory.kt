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
        fun toName(
            category: Int
        ): String {
            return when(category){
                1->"가슴"
                2->"어깨"
                3->"등"
                4->"팔"
                5->"하체"
                else->"맨몸"
            }
        }
    }
}