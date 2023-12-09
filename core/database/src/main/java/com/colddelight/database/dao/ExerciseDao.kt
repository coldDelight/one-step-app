package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colddelight.database.model.ExerciseEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    fun getExercise(): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Query("DELETE FROM exercise WHERE id=(:id)")
    suspend fun delExercise(id: Int)
}