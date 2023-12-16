package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.database.dateFormat
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.History
import com.colddelight.network.model.NetworkExercise
import com.colddelight.network.model.NetworkHistory
import java.util.Date

@Entity(tableName = "history")
data class HistoryEntity(
    //이건 오늘 운동 했는지를 파악하기 위한것 외래키 설정이 아님
    @ColumnInfo(name = "routine_id") val routineId: Int,
    @ColumnInfo(name = "created_time") val createdTime: Date,
    @ColumnInfo(name = "category_list") val categoryList: List<Int>,
    @ColumnInfo(name = "total_time") val totalTime: String,
    //홈에서 오늘 운동 한지 안한지
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "is_free") val isFree: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

//fun HistoryEntity.asHistory() = History(
//    id = id,
//    routineId = routineId,
//    createdTime = createdTime,
//    categoryList = categoryList,
//    totalTime = totalTime,
//    isDone = isDone,
//    isFree = isFree,
//)

//fun HistoryEntity.asNetworkHistory() = NetworkHistory(
//    room_id = id,
//    routine_id = routineId,
//    created_time = dateFormat.format(createdTime),
//    category_list = categoryList,
//    total_time = totalTime,
//    isDone = isDone,
//    isFree = isFree,
//)