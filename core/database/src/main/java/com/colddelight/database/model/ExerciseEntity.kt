package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.model.Exercise
import com.colddelight.network.model.NetworkExercise

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun ExerciseEntity.asExercise() = Exercise(
    id = id,
    name = name,
    category = category
)

fun ExerciseEntity.asNetworkExercise() = NetworkExercise(
    room_id = id,
    name = name,
    category = category
)
