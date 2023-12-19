package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colddelight.database.model.DayExerciseEntity
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.HistoryExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryExerciseDao {

    @Query("SELECT * FROM history_exercise JOIN exercise ON history_exercise.exercise_id = exercise.id WHERE history_exercise.history_id=(:historyId)")
    fun getTodayHistoryExercises(historyId: Int): Flow<Map<HistoryExerciseEntity, ExerciseEntity>>

    @Query("SELECT * FROM history_exercise ")
    fun getHistoryExercises(): Flow<List<HistoryExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryExercise(historyExerciseEntity: HistoryExerciseEntity)
}