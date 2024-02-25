package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.colddelight.database.model.ExerciseEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    fun getExercise(): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertExercise(exercise: ExerciseEntity)


    @Transaction
    suspend fun deleteExerciseAndRelatedData(exerciseId: Int) {
        deleteDayExercisesByExerciseId(exerciseId)
        deleteHistoryExercisesByExerciseId(exerciseId)
        deleteExerciseById(exerciseId)
    }

    @Query("DELETE FROM exercise WHERE id = :exerciseId")
    suspend fun deleteExerciseById(exerciseId: Int)

    @Query("DELETE FROM day_exercise WHERE exercise_id = :exerciseId")
    suspend fun deleteDayExercisesByExerciseId(exerciseId: Int)

    @Query("DELETE FROM history_exercise WHERE exercise_id = :exerciseId")
    suspend fun deleteHistoryExercisesByExerciseId(exerciseId: Int)

}