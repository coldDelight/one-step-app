package com.colddelight.database.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colddelight.database.model.HistoryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.Date

@Dao
interface HistoryDao {


//
    @Query("SELECT history.id FROM history WHERE created_time = :today")
    fun getHistoryForToday(today: LocalDate): Flow<Int>

    @Query("SELECT created_time FROM history WHERE created_time >= :startDate AND is_done = 1")
    fun getHistoryForThisWeek(startDate: LocalDate): Flow<List<LocalDate>>

    @Query("SELECT created_time FROM history WHERE created_time >= :startDate AND is_done = 1")
    fun getHistoryForSelectedMonth(startDate: LocalDate): Flow<List<LocalDate>>

    @Query("SELECT created_time FROM history WHERE is_done = 1")
    fun getAllDoneHistory(): Flow<List<LocalDate>>

    // 히스토리 아이디임
//    @Query("UPDATE history SET is_done = 1 WHERE id = (:id)")
//    fun updateHistory(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

}