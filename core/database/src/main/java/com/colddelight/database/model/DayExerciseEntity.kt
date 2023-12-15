package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.database.dateFormat
import com.colddelight.model.BodyWeight
import com.colddelight.model.DayExercise
import com.colddelight.network.model.NetworkBodyWeight
import com.colddelight.network.model.NetworkDayExercise

@Entity(tableName = "day_exercise")
data class DayExerciseEntity(

    @ColumnInfo(name = "routine_day_id") val routineDayId: Int,
    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "origin") val origin: Int,
    @ColumnInfo(name = "kg_list") val kgList: String,
    @ColumnInfo(name = "reps_list") val repsList: String,

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun DayExerciseEntity.asDayExercise() = DayExercise(
    id = id,
    routineDayId = routineDayId,
    exerciseId = exerciseId,
    index = index,
    origin = origin,
    kgList = kgList,
    repsList = repsList
)

fun DayExerciseEntity.asNetworkDayExercise() = NetworkDayExercise(
    room_id = id,
    routine_day_id = routineDayId,
    exercise_id = exerciseId,
    index = index,
    origin = origin,
    kg_list = kgList,
    reps_list = repsList

)