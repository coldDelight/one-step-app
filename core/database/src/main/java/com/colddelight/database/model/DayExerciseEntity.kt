package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_exercise")
data class DayExerciseEntity(

    @ColumnInfo(name = "routine_day_id") val routineDayId: Int,
    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "kg_list") val kgList: List<Int>,
    @ColumnInfo(name = "reps_list") val repsList: List<Int>,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)



//fun DayExerciseEntity.asNetworkDayExercise() = NetworkDayExercise(
//    room_id = id,
//    routine_day_id = routineDayId,
//    exercise_id = exerciseId,
//    index = index,
//    origin = origin,
//    kg_list = kgList,
//    reps_list = repsList
//
//)