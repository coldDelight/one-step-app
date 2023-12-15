package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.model.FreeExercise
import com.colddelight.network.model.NetworkFreeExercise

@Entity(tableName = "free_exercise")
data class FreeExerciseEntity(
    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
    @ColumnInfo(name = "history_id") val historyId: Int,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "origin") val origin: Int,
    @ColumnInfo(name = "kg_list") val kgList: String,
    @ColumnInfo(name = "reps_list") val repsList: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun FreeExerciseEntity.asFreeExercise() = FreeExercise(
    id = id,
    exerciseId = exerciseId,
    index = index,
    origin = origin,
    kgList = kgList,
    historyId = historyId,
    repsList = repsList
)

fun FreeExerciseEntity.asNetworkFreeExercise() = NetworkFreeExercise(
    room_id = id,
    exercise_id = exerciseId,
    index = index,
    origin = origin,
    historyId = historyId,
    kg_list = kgList,
    reps_list = repsList

)