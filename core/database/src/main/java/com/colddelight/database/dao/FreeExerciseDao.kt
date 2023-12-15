package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.FreeExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FreeExerciseDao {
    @Query("SELECT * FROM free_exercise JOIN exercise ON free_exercise.exercise_id = exercise.id WHERE history_id=(:historyId)")
    fun getAllFreeExercise(historyId: Int): Flow<Map<FreeExerciseEntity, ExerciseEntity>>

    @Query("SELECT * FROM free_exercise JOIN exercise ON free_exercise.exercise_id = exercise.id WHERE free_exercise.id=(:freeExerciseId)")
    fun getFreeExercise(freeExerciseId: Int): Flow<Map<FreeExerciseEntity, ExerciseEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFreeExercise(freeExerciseEntity: FreeExerciseEntity)

    @Query("DELETE FROM free_exercise WHERE id=(:id)")
    suspend fun delFreeExercise(id: Int)

}