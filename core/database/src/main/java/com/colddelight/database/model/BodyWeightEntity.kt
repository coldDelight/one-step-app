package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "body_weight")
data class BodyWeightEntity(
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "created_time") val createdTime: LocalDate,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)


//
//fun BodyWeightEntity.asNetworkBodyWeight() = NetworkBodyWeight(
//    room_id = id,
//    created_time = dateFormat.format(createdTime),
//    weight = weight,
//)
