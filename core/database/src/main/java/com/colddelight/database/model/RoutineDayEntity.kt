package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.model.Exercise

@Entity(tableName = "routine_day")
data class RoutineDayEntity(
    @ColumnInfo(name = "routine_id") val routineId: Int,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: Int,
    @ColumnInfo(name = "category_list") val categoryList: List<Int>,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)


