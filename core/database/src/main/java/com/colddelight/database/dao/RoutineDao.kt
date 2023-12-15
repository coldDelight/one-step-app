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

    @Query("SELECT * FROM routine")
    fun getAllRoutine(): Flow<List<RoutineEntity>>

    @Query("SELECT * FROM routine WHERE id = :id")
    fun getRoutine(id: Int): Flow<RoutineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: RoutineEntity)

    //삭제하려고 하는 Routine id를 쓰고있는 routine_day삭제 -> day_exercise도 같이 삭제 해야함
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