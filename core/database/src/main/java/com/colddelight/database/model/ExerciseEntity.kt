package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.model.Exercise

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "categoryName") val categoryName: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun ExerciseEntity.asExternalModel() = Exercise(
    name = name,
    categoryName = categoryName
)
