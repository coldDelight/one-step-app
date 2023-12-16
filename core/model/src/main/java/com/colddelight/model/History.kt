package com.colddelight.model

import java.util.Date

data class History(
    val routineId: Int,
    val createdTime: Date,
    val categoryList: List<ExerciseCategory>,
    val totalTime: String,
    val isDone: Boolean,
    val isFree: Boolean,
    val id: Int,
)

