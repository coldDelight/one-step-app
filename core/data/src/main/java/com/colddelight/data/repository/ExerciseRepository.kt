package com.colddelight.data.repository

import com.colddelight.model.Exercise
import com.colddelight.model.TodayRoutine
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    fun getTodayExerciseList(routineDayId: Int, historyId: Int): Flow<List<Exercise>>
    fun getTodayRoutineInfo(): Flow<TodayRoutine>

    suspend fun upDateKgList(historyExerciseId: Int, kgList: List<Int>)
    suspend fun upDateRepsList(historyExerciseId: Int, repsList: List<Int>)

    suspend fun addTmp()

}