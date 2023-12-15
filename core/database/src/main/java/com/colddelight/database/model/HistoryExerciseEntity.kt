package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.model.History
import com.colddelight.model.HistoryExercise
import com.colddelight.network.model.NetworkExercise
import com.colddelight.network.model.NetworkHistoryExercise
import java.util.Date

@Entity(tableName = "history_exercise")
data class HistoryExerciseEntity(
    @ColumnInfo(name = "history_id") val historyId: Int,
    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "origin") val origin: Int,
    @ColumnInfo(name = "kg_list") val kgList: String,
    @ColumnInfo(name = "reps_list") val repsList: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun HistoryExerciseEntity.asHistoryExerciseEntity() = HistoryExercise(
    id = id,
    historyId = historyId,
    exerciseId = exerciseId,
    index = index,
    origin = origin,
    kgList = kgList,
    repsList = repsList,
)

fun HistoryExerciseEntity.asNetworkHistoryExercise() = NetworkHistoryExercise(
    room_id = id,
    history_id = historyId,
    exercise_id = exerciseId,
    index = index,
    origin = origin,
    kg_list = kgList,
    reps_list = repsList,
)