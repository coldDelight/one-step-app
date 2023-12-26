package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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

//    @Transaction
//    @Query("SELECT * FROM day_exercise WHERE routine_day_id = :routineDayId")
//    suspend fun convertDayExercisesAndInsertHistory(routineDayId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryExercise(historyExerciseEntity: HistoryExerciseEntity)

    @Query("UPDATE history_exercise SET kg_list = :kgList WHERE id = :historyExerciseId")
    suspend fun updateKgList(historyExerciseId: Int, kgList: List<Int>)

    @Query("UPDATE history_exercise SET reps_list = :repsList WHERE id = :historyExerciseId")
    suspend fun updateRepsList(historyExerciseId: Int, repsList: List<Int>)


    // Insert a list of HistoryExerciseEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(historyExercises: List<HistoryExerciseEntity>)

}