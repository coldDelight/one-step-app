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
    suspend fun insertExercise(exercise: ExerciseEntity)


    // exercise ID를 사용하는 day_exercise, history_exercise 미리 삭제
    @Transaction
    suspend fun deleteExerciseAndRelatedData(exerciseId: Int) {
        deleteDayExercisesByExerciseId(exerciseId)
        deleteHistoryExercisesByExerciseId(exerciseId)
        deleteFreeExercisesByExerciseId(exerciseId)
        deleteExerciseById(exerciseId)
    }

    @Query("DELETE FROM exercise WHERE id = :exerciseId")
    suspend fun deleteExerciseById(exerciseId: Int)

    @Query("DELETE FROM day_exercise WHERE exercise_id = :exerciseId")
    suspend fun deleteDayExercisesByExerciseId(exerciseId: Int)

    @Query("DELETE FROM history_exercise WHERE exercise_id = :exerciseId")
    suspend fun deleteHistoryExercisesByExerciseId(exerciseId: Int)

    @Query("DELETE FROM free_exercise WHERE exercise_id = :exerciseId")
    suspend fun deleteFreeExercisesByExerciseId(exerciseId: Int)
}