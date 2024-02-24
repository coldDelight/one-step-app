package com.colddelight.data.repository

import com.colddelight.model.DayExercise
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import kotlinx.coroutines.flow.Flow

interface RoutineRepository2 {
    fun getRoutineWeekInfo(): Flow<List<RoutineDayInfo>>


    suspend fun insertRoutineDay(routineDay: RoutineDayInfo)

    suspend fun insertExercise(exercise: ExerciseInfo)

    suspend fun insertDayExercise(dayExercise: DayExercise)


    suspend fun deleteRoutineDay(routineDayId: Int)

    suspend fun deleteExercise(exerciseId: Int)

    suspend fun deleteDayExercise(dayExerciseId: Int)

    fun getAllExerciseList(): Flow<List<ExerciseInfo>>
}