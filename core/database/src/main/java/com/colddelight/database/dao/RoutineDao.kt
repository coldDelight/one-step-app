package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.colddelight.database.model.RoutineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {

    @Query("SELECT * FROM routine WHERE id = :id")
    fun getRoutine(id: Int): Flow<RoutineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRoutine(routine: RoutineEntity)

    @Query("UPDATE routine SET cnt = cnt + 1 WHERE id = :routineId")
    suspend fun updateCountById(routineId: Int)
    @Transaction
    suspend fun deleteRoutineAndRelatedData(routineId: Int) {
        deleteDayExercisesByRoutineId(routineId)
        deleteRoutineDaysByRoutineId(routineId)
        deleteRoutineById(routineId)
    }

    @Query("DELETE FROM routine WHERE id = :routineId")
    suspend fun deleteRoutineById(routineId: Int)

    @Query("DELETE FROM routine_day WHERE routine_id = :routineId")
    suspend fun deleteRoutineDaysByRoutineId(routineId: Int)

    @Query("DELETE FROM day_exercise WHERE routine_day_id IN (SELECT id FROM routine_day WHERE routine_id = :routineId)")
    suspend fun deleteDayExercisesByRoutineId(routineId: Int)
}