package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.colddelight.database.model.HistoryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface HistoryDao {
    @Query("SELECT history.id FROM history WHERE created_time = :today")
    fun getHistoryForToday(today: LocalDate=LocalDate.now()): Flow<Int>

    @Query("SELECT created_time FROM history WHERE created_time >= :startDate AND is_done = 1")
    fun getHistoryForThisWeek(startDate: LocalDate): Flow<List<LocalDate>>

    @Query("SELECT * FROM history WHERE created_time >= :startDate AND created_time <= :endDate AND is_done = 1")
    fun getHistoryForSelectedMonth(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<HistoryEntity>>

    @Query("SELECT created_time FROM history WHERE is_done = 1")
    fun getAllDoneHistory(): Flow<List<LocalDate>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)


    @Transaction
    suspend fun finToday(historyId: Int) {
        finHistory(historyId)
        finHistoryExercises(historyId)
    }

    @Query("UPDATE history SET is_done = 1 WHERE id = :historyId")
    suspend fun finHistory(historyId: Int)

    @Query("UPDATE history_exercise SET is_done = 1 WHERE history_id = :historyId")
    suspend fun finHistoryExercises(historyId: Int)

    @Transaction
    suspend fun deleteHistory(historyId: Int) {
        deleteHistoryById(historyId)
        deleteHistoryExerciseByHistoryId(historyId)
    }

    @Query("DELETE FROM history WHERE id = :historyId AND is_done = 0")
    suspend fun deleteHistoryById(historyId: Int)

    @Query("DELETE FROM history_exercise WHERE history_id = :historyId")
    suspend fun deleteHistoryExerciseByHistoryId(historyId: Int)

}