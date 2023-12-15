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
interface RoutineDayDao {


    @Query("SELECT * FROM routine_day WHERE routine_id=(:routineId)")
    fun getAllRoutineDay(routineId: Int): Flow<List<RoutineDayEntity>>

    @Query("SELECT * FROM routine JOIN routine_day ON (:dayOfWeek)=routine_day.day_of_week WHERE routine_id = (:routineId)")
    fun getTodayRoutineInfo(
        routineId: Int,
        dayOfWeek: Int
    ): Flow<Map<RoutineEntity, @MapColumn("category_list") String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutineDay(routineDay: RoutineDayEntity)

    @Transaction
    suspend fun deleteRoutineDayAndRelatedData(routineDayId: Int) {
        deleteDayExercisesByRoutineDayId(routineDayId)
        deleteRoutineDayById(routineDayId)
    }

    @Query("DELETE FROM routine_day WHERE id = :routineDayId")
    suspend fun deleteRoutineDayById(routineDayId: Int)

    @Query("DELETE FROM day_exercise WHERE routine_day_id = :routineDayId")
    suspend fun deleteDayExercisesByRoutineDayId(routineDayId: Int)

}