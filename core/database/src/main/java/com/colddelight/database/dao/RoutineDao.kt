package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.colddelight.database.model.RoutineDayEntity
import com.colddelight.database.model.RoutineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {

    @Query("SELECT * FROM routine WHERE id = :id")
    fun getRoutine(id: Int): Flow<RoutineEntity>

    @Query("SELECT * FROM routine JOIN routine_day ON (:dayOfWeek)=routine_day.day_of_week WHERE routine_day.routine_id = (:routineId)")
    fun getTodayRoutine(
        routineId: Int,
        dayOfWeek: Int
    ): Flow<Map<RoutineEntity, RoutineDayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRoutine(routine: RoutineEntity)
}