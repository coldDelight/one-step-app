package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.database.dateFormat
import com.colddelight.model.BodyWeight
import com.colddelight.network.model.NetworkBodyWeight
import java.util.Date

@Entity(tableName = "body_weight")
data class BodyWeightEntity(
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "created_time") val createdTime: Date,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun BodyWeightEntity.asBodyWeight() = BodyWeight(
    id = id,
    createdTime = createdTime,
    weight = weight
)

fun BodyWeightEntity.asNetworkBodyWeight() = NetworkBodyWeight(
    room_id = id,
    created_time = dateFormat.format(createdTime),
    weight = weight,
)
