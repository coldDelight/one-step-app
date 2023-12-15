package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.model.RoutineDay
import com.colddelight.network.model.NetworkExercise
import com.colddelight.network.model.NetworkRoutineDay

@Entity(tableName = "routine_day")
data class RoutineDayEntity(
    @ColumnInfo(name = "routine_id") val routineId: Int,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: Int,
    @ColumnInfo(name = "category_list") val categoryList: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)


fun RoutineDayEntity.asRoutineDay() = RoutineDay(
    id = id,
    routineId = routineId,
    dayOfWeek = dayOfWeek,
    categoryList = categoryList,
)

fun RoutineDayEntity.asNetworkRoutineDay() = NetworkRoutineDay(
    room_id = id,
    routine_id = routineId,
    day_of_week = dayOfWeek,
    category_list = categoryList,
)