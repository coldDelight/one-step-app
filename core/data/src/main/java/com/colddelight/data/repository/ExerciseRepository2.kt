package com.colddelight.data.repository

import com.colddelight.model.HistoryExerciseUI
import com.colddelight.model.SetInfo
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository2 {

    fun getTodayExerciseList(): Flow<List<HistoryExerciseUI>>

    suspend fun upDateKgList(historyExerciseId: Int, kgList: List<Int>)
    suspend fun upDateRepsList(historyExerciseId: Int, repsList: List<Int>)
    suspend fun upDateSetInfo(historyExerciseId: Int, kgList: List<Int>, repsList: List<Int>)

    suspend fun initExercise()
    suspend fun updateHistoryExercise(id: Int, isDone: Boolean)
    suspend fun updateDayExercise(id: Int, setInfoList: List<SetInfo>)
    suspend fun finHistory()

}