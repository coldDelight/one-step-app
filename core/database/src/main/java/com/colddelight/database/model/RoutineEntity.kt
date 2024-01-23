package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "routine")
data class RoutineEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "created_time") val createdTime: LocalDate = LocalDate.now(),
    @ColumnInfo(name = "cnt") val cnt: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    companion object {
        private const val DEFAULT_ROUTINE_ID = 1
        val DEFAULT_Routine =
            RoutineEntity(id = DEFAULT_ROUTINE_ID, name = "내 루틴", createdTime = LocalDate.now(), cnt = 0)
    }
}
