package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colddelight.database.model.BodyWeightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BodyWeightDao {

    @Query("SELECT * FROM body_weight")
    fun getBodyWeight(): Flow<List<BodyWeightEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBodyWeight(bodyWeight: BodyWeightEntity)

    @Query("DELETE FROM body_weight WHERE id=(:id)")
    suspend fun delBodyWeight(id: Int)
}