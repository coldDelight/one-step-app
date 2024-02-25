package com.colddelight.data.repository

import com.colddelight.model.DayExerciseWithExercise
import kotlinx.coroutines.flow.Flow

interface HistoryExerciseRepository {

    suspend fun deleteHistoryExercise(historyExerciseId: Int)

    suspend fun insertHistoryExercise(id:Int,todayHistoryId:Int,dayExercise: DayExerciseWithExercise)

}