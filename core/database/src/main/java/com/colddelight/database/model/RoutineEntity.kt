package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.database.dateFormat
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDay
import com.colddelight.network.model.NetworkExercise
import com.colddelight.network.model.NetworkRoutine
import java.util.Date

@Entity(tableName = "routine")
data class RoutineEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "created_time") val createdTime: Date,
    @ColumnInfo(name = "cnt") val cnt: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    companion object {
        const val DEFAULT_ROUTINE_ID = 1
        val DEFAULT_Routine =
            RoutineEntity(id = DEFAULT_ROUTINE_ID, name = "내 루틴", createdTime = Date(), cnt = 0)
    }
}

fun RoutineEntity.asRoutine() = Routine(
    id = id,
    name = name,
    createdTime = createdTime,
    cnt = cnt,
)

fun RoutineEntity.asNetworkRoutine() = NetworkRoutine(
    room_id = id,
    name = name,
    created_time = dateFormat.format(createdTime),
    cnt = cnt,
)