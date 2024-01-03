package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "history")
data class HistoryEntity(
    @ColumnInfo(name = "created_time") val createdTime: LocalDate,
    @ColumnInfo(name = "category_list") val categoryList: List<Int>,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
