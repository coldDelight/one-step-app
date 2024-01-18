package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_exercise")
data class HistoryExerciseEntity(
    @ColumnInfo(name = "history_id") val historyId: Int,
    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
    @ColumnInfo(name = "day_exercise_id") val dayExerciseId: Int,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "kg_list") val kgList: List<Int>,
    @ColumnInfo(name = "reps_list") val repsList: List<Int>,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
