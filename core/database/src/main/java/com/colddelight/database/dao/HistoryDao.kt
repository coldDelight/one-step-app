package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colddelight.database.model.HistoryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.Date

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history WHERE routine_id = (:id)")
    fun getAllHistory(id: Int): Flow<List<HistoryEntity>>
    @Query("SELECT * FROM history WHERE created_time = (:createTime)")
    fun getTodayHistory(createTime: LocalDate): Flow<HistoryEntity>


    // 히스토리 아이디임
    @Query("UPDATE history SET is_done = 1 WHERE id = (:id)")
    fun updateHistory(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

}