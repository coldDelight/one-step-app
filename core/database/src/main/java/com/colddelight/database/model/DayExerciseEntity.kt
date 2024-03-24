package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_exercise")
data class DayExerciseEntity(

    @ColumnInfo(name = "routine_day_id") val routineDayId: Int,
    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
    @ColumnInfo(name = "kg_list") val kgList: List<Int>,
    @ColumnInfo(name = "reps_list") val repsList: List<Int>,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)


