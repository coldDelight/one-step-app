package com.colddelight.data.repository

import com.colddelight.model.HistoryExercise
import com.colddelight.model.HistoryExerciseUI

interface HistoryExerciseRepository {

    suspend fun deleteHistoryExercise(historyExerciseId: Int)
    fun getHistoryExercise(historyId: Int)

//    suspend fun insertHistoryExercise(id:Int,todayHistoryId:Int,dayExercise: DayExerciseWithExercise)
    suspend fun insertHistoryExercise(historyExercise: HistoryExercise)

}